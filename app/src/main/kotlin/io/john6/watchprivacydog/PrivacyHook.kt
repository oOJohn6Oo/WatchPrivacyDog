package io.john6.watchprivacydog

import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.john6.watchprivacydog.data.HookInfo
import io.john6.watchprivacydog.di.AppModule


class PrivacyHook : IXposedHookLoadPackage {

    private var context: Context? = null

    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam?) {
        loadPackageParam ?: return
        if(loadPackageParam.packageName == WatchDogBroadcastReceiver.myPackageName) return
        if (!AppModule.shouldHook) return

        val contextClass = XposedHelpers.findClass("android.content.ContextWrapper", loadPackageParam.classLoader)
        XposedHelpers.findAndHookMethod(contextClass, "getApplicationContext", object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
                if(context == null) {
                    context = param.result as? Context
                    WatchDogBroadcastReceiver.notifyNewAppHooked(context)
                }
            }
        })

        val packageName = loadPackageParam.packageName
        XposedBridge.log("PrivacyHook: $packageName")

        AppModule.allHookInfo.forEach {
            tryHookMethod(it)
        }
    }

    private fun tryHookMethod(it: HookInfo) {
        try {
            XposedHelpers.findAndHookMethod(
                it.clazz,
                it.methodName,
                *(it.params),
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        super.beforeHookedMethod(param)
                        if (!AppModule.shouldHook) return
                        XposedBridge.log("PrivacyHook: ${it.methodName} invocation catch ${context?.packageName}")
                        if (it.shouldPrintStackTrace(param)) {
                            val hookItemInfo = it.generateData2Save()
                            WatchDogBroadcastReceiver.notifyNewHookItemInfo(context, hookItemInfo)
                            printStackTrace(hookItemInfo.displayName)
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