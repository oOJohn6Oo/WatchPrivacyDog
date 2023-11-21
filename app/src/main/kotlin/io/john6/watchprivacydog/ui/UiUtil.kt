package io.john6.watchprivacydog.ui

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt


val Int.vdpf
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
val Int.vdp
    get() = this.vdpf.roundToInt()
