package io.john6.watchprivacydog.data

import androidx.annotation.StringRes
import de.robv.android.xposed.XC_MethodHook

data class HookInfo(
    val printName: String,
    @StringRes val desc: Int = 0,
    val clazz: Class<*>,
    val methodName: String,
    val params: Array<Any> = emptyArray(),
    val shouldPrintStackTrace: (XC_MethodHook.MethodHookParam) -> Boolean = { true }
) {
    fun generateData2Save(): HookItemInfo {
        val time = System.currentTimeMillis()
        return HookItemInfo(printName, time, Thread.currentThread().stackTrace.map { it.methodName })
    }
}