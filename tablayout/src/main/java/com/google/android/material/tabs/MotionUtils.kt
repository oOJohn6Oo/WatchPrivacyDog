package com.google.android.material.tabs

import android.animation.TimeInterpolator
import android.content.Context
import android.util.TypedValue
import android.view.animation.AnimationUtils
import androidx.annotation.AttrRes


object MotionUtils {
    /**
     * Load an interpolator from a material easing theme attribute.
     *
     * @param context context from where the theme attribute will be resolved
     * @param attrResId the `motionEasing*` theme attribute to resolve
     * @param defaultInterpolator the interpolator to be returned if unable to resolve `attrResId`.
     * @return the resolved [TimeInterpolator] which `attrResId` points to or the `defaultInterpolator` if resolution was unsuccessful.
     */
    @JvmStatic
    fun resolveThemeInterpolator(
        context: Context,
        @AttrRes attrResId: Int,
        defaultInterpolator: TimeInterpolator
    ): TimeInterpolator {
        val easingValue = TypedValue()
        if (!context.theme.resolveAttribute(attrResId, easingValue, true)) {
            return defaultInterpolator
        }
        require(easingValue.type == TypedValue.TYPE_STRING) {
            ("Motion easing theme attribute must be an @interpolator resource for"
                    + " ?attr/motionEasing*Interpolator attributes or a string for"
                    + " ?attr/motionEasing* attributes.")
        }

        return AnimationUtils.loadInterpolator(context, easingValue.resourceId)
    }
}