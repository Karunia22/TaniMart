package com.example.tanimart.ui.address.data

data class Address(
    val id: Int,
    val recipientName: String,
    val phone: String,
    val address: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val isPrimary: Boolean
)