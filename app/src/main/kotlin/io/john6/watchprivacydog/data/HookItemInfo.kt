package io.john6.watchprivacydog.data

data class HookItemInfo(
    val displayName:String,
    val invocationTime:Long,
    val stackTrace:List<String>
)
