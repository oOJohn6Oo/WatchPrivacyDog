package io.john6.watchprivacydog

import android.app.Activity
import android.content.ContentProvider
import android.provider.Settings
import java.util.LinkedList
import java.util.Objects

/**
 * [Map.keys] packageName, [Map.values]  function invocation info
 *
 * [Triple.first] Function name
 * [Triple.second] Invocation time
 * [Triple.third] Invocation stack trace
 */
val stackTraceMap = hashMapOf<String, LinkedList<Triple<String, Long, List<String>>>>()





data class HookInfo(
    val clazz: Class<*>,
    val methodName: String,
    val params: List<Any>,
)

val allHookInfo = listOf(
    HookInfo(Settings::class.java, "getString", listOf(ContentProvider::class.java, String::class.java))
)