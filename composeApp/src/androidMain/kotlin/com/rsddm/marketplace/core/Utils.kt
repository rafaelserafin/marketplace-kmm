package com.rsddm.marketplace.core

import java.text.NumberFormat
import java.util.Locale

private const val CURRENCY_SIGN = "R$"

fun String.toDoubleFromCurrency(): Double {
    val cleanValue = this.replace(CURRENCY_SIGN, "").trim()


    return NumberFormat.getInstance(Locale("pr", "BR")).parse(cleanValue)?.toDouble() ?: 0.0
}

fun Double.toStringCurrency(): String {
    val value = NumberFormat.getInstance(Locale("pr", "BR")).format(Math.round(this * 100.0f) / 100.0f)
    return "$CURRENCY_SIGN $value"
}