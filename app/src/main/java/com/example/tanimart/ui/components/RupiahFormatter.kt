package com.example.tanimart.ui.components

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(price: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale("id", "ID"))
        .format(price)
        .replace(",00", "")
        .replace("Rp", "Rp")
        .trim()
}