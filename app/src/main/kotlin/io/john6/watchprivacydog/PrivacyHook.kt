package io.john6.watchprivacydog

import android.app.Activity
import android.os.Bundle
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File
import java.util.LinkedList


class PrivacyHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam?) {
        loadPackageParam ?: return

        val packageName = loadPackageParam.packageName
        stackTraceMap[packageName] = LinkedList()
        XposedHelpers.findAndHookMethod(
            Activity::class.java,
            "onCreate",
            Bundle::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    val stackTraceString =
                        Thread.currentThread().stackTrace.joinToString(System.lineSeparator())
                    Log.d(
                        "PrivacyHook",
                        "onCreate invoked by ${System.lineSeparator()}${stackTraceString}"
                    )
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                }
            })
    }
}