package com.google.android.material.tabs

object AnimationUtils {

    /** Linear interpolation between `startValue` and `endValue` by `fraction`.  */
    @JvmStatic
    fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
        return startValue + fraction * (endValue - startValue)
    }

    /** Linear interpolation between `startValue` and `endValue` by `fraction`.  */
    @JvmStatic
    fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
        return startValue + Math.round(fraction * (endValue - startValue))
    }

    /**
     * Linear interpolation between `outputMin` and `outputMax` when `value` is
     * between `inputMin` and `inputMax`.
     *
     *
     * Note that `value` will be coerced into `inputMin` and `inputMax`.This
     * function can handle input and output ranges that span positive and negative numbers.
     */
    @JvmStatic
    fun lerp(
        outputMin: Float, outputMax: Float, inputMin: Float, inputMax: Float, value: Float
    ): Float {
        if (value <= inputMin) {
            return outputMin
        }
        return if (value >= inputMax) {
            outputMax
        } else lerp(outputMin, outputMax, (value - inputMin) / (inputMax - inputMin))
    }
}