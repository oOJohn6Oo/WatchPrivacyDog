package io.john6.watchprivacydog.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HookItemInfo(
    val displayName:String,
    val invocationTime:Long,
    val stackTrace:List<String>
) : Parcelable
