package br.com.mybank.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object DecimalFormatMonetary : DecimalFormat() {
    private fun newInstance(func: DecimalFormat.() -> Unit): DecimalFormat {
        val instance = NumberFormat.getCurrencyInstance(Locale("pt", "br")) as DecimalFormat
        return instance.apply { func() }
    }

    fun getIntance() = newInstance {
        val symbol = "R$ "
        negativePrefix = "-$symbol "
        negativeSuffix = ""
        positivePrefix = "$symbol "
        maximumFractionDigits = 2

        with(decimalFormatSymbols) {
            currencySymbol = symbol
            groupingSeparator = '.'
            monetaryDecimalSeparator = '.'
        }
    }
}