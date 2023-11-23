package com.google.android.material.tabs

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat


object MaterialResources {
    /**
     * Returns the drawable object from the given attributes.
     *
     *
     * This method supports inflation of `<vector>` and `<animated-vector>` resources
     * on devices where platform support is not available.
     */
    @JvmStatic
    fun getDrawable(
        context: Context, attributes: TypedArray, @StyleableRes index: Int
    ): Drawable? {
        if (attributes.hasValue(index)) {
            val resourceId = attributes.getResourceId(index, 0)
            if (resourceId != 0) {
                val value = AppCompatResources.getDrawable(context, resourceId)
                if (value != null) {
                    return value
                }
            }
        }
        return attributes.getDrawable(index)
    }

    /**
     * Returns the [ColorStateList] from the given [TypedArray] attributes. The resource
     * can include themeable attributes, regardless of API level.
     */
    @JvmStatic
    fun getColorStateList(
        context: Context, attributes: TypedArray, @StyleableRes index: Int
    ): ColorStateList? {
        if (attributes.hasValue(index)) {
            val resourceId = attributes.getResourceId(index, 0)
            if (resourceId != 0) {
                val value = AppCompatResources.getColorStateList(context, resourceId)
                if (value != null) {
                    return value
                }
            }
        }

        return attributes.getColorStateList(index)
    }

}
/**
 * 与 {@see MaterialColors#getColor}功能相同
 */
fun Context.getAttrColor(@AttrRes colorAttrId: Int) =
    TypedValue().let { tv ->
        this.theme.resolveAttribute(colorAttrId, tv, true)
        if (tv.resourceId != 0)
            ContextCompat.getColor(this, tv.resourceId)
        else tv.data
    }