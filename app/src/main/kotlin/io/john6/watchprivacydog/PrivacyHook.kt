package io.john6.watchprivacydog

import android.os.Process
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.john6.watchprivacydog.data.HookInfo
import io.john6.watchprivacydog.data.HookItemInfo
import io.john6.watchprivacydog.di.AppModule
import java.util.LinkedList


class PrivacyHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam?) {
        loadPackageParam ?: return
        if(loadPackageParam.packageName == "io.john6.watchprivacydog") return
        if (!AppModule.shouldHook) return

        val packageName = loadPackageParam.packageName
        XposedBridge.log("PrivacyHook: $packageName")

        AppModule.stackTraceAppList.add(packageName)
        XposedBridge.log("PrivacyHook: ${AppModule.stackTraceAppList.size}")
        val traceList = LinkedList<HookItemInfo>()
        AppModule.stackTraceMap[packageName] = traceList

        AppModule.allHookInfo.forEach {
            tryHookMethod(it, traceList)
        }
    }

    private fun tryHookMethod(
        it: HookInfo,
        traceList: LinkedList<HookItemInfo>
    ) {
        try {
            XposedHelpers.findAndHookMethod(
                it.clazz,
                it.methodName,
                *(it.params),
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        super.beforeHookedMethod(param)
                        XposedBridge.log("PrivacyHook: ${it.methodName} invocation catch")
                        if (!AppModule.shouldHook) return
                        if (it.shouldPrintStackTrace(param)) {
                            doSaveStackTrace(it, traceList)
                        }
                    }

                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                    }
                })
            XposedBridge.log("PrivacyHook: ${it.methodName} hooked")
        } catch (e: Exception) {
            XposedBridge.log(e)
        }
    }

    private fun doSaveStackTrace(
        hookInfo: HookInfo,
        traceList: LinkedList<HookItemInfo>
    ) {
        val info = hookInfo.generateData2Save()
        traceList.add(info)
        printStackTrace(info.displayName)
    }
    private fun printStackTrace(msg: String) {

        val sb = StringBuilder()
        Thread.currentThread().stackTrace.let { trace ->
            sb.append(msg).append(System.lineSeparator())
            for (traceElement in trace) {
                sb.append("\tat $traceElement").append(System.lineSeparator())
            }

            XposedBridge.log(sb.toString())
        }
    }

}