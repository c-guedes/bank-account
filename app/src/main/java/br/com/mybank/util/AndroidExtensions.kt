package br.com.mybank.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

private val FORMAT_WITH_SYMBOL = DecimalFormatMonetary.getIntance()

fun Activity.setStatusBarColor(@ColorRes colorId: Int, hasLightTextColor: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = getResColor(colorId)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (hasLightTextColor) window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun Double.formatValueToMonetary() =
    FORMAT_WITH_SYMBOL.decimalFormatSymbols.currencySymbol.plus(this)

fun Context.getResColor(@ColorRes idColor: Int) = ContextCompat.getColor(this, idColor)