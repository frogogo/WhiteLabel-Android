package ru.frogogo.whitelabel.extension

import android.content.res.Resources

/**
 * Converts Int Px value to Dp value
 */
fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts Int Dp value to Px value
 */
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts Float Px value to Dp value
 */
fun Float.pxToDp(): Float = this / Resources.getSystem().displayMetrics.density

/**
 * Converts Float Dp value to Px value
 */
fun Float.dpToPx(): Float = this * Resources.getSystem().displayMetrics.density

/**
 * Converts Int Sp value to Px value
 */
fun Int.spToPx(): Float = this * Resources.getSystem().displayMetrics.scaledDensity
