package com.google.android.material.tabs

import android.content.Context
import android.graphics.PorterDuff
import android.util.TypedValue


object ViewUtils {


    @JvmStatic
    fun dpToPx(context: Context, value:Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        )
    }

    @JvmStatic
    fun parseTintMode(value: Int, defaultMode: PorterDuff.Mode?): PorterDuff.Mode? {
        return when (value) {
            3 -> PorterDuff.Mode.SRC_OVER
            5 -> PorterDuff.Mode.SRC_IN
            9 -> PorterDuff.Mode.SRC_ATOP
            14 -> PorterDuff.Mode.MULTIPLY
            15 -> PorterDuff.Mode.SCREEN
            16 -> PorterDuff.Mode.ADD
            else -> defaultMode
        }
    }
}