package com.example.tanimart.data.address

import com.example.tanimart.ui.address.data.Address

class AddressRepository(
    private val api: AddressApi
) {

    suspend fun getAddresses(): List<Address> {
        return api.getAddresses()
            .addresses
            .map { it.toAddress() }
    }

    suspend fun addAddress(
        recipientName: String,
        phone: String,
        address: String,
        city: String,
        province: String,
        postalCode: String,
        isPrimary: Boolean = false
    ): Address {
        return api.addAddress(
            AddressRequest(
                recipientName = recipientName,
                phone = phone,
                address = address,
                city = city,
                province = province,
                postalCode = postalCode,
                isPrimary = isPrimary
            )
        ).address.toAddress()
    }

    suspend fun updateAddress(
        id: Int,
        recipientName: String,
        phone: String,
        address: String,
        city: String,
        province: String,
        postalCode: String,
        isPrimary: Boolean = false
    ): Address {
        return api.updateAddress(
            id = id,
            request = AddressRequest(
                recipientName = recipientName,
                phone = phone,
                address = address,
                city = city,
                province = province,
                postalCode = postalCode,
                isPrimary = isPrimary
            )
        ).address.toAddress()
    }

    suspend fun deleteAddress(id: Int) {
        api.deleteAddress(id)
    }

    suspend fun setPrimary(id: Int): Address {
        return api.setPrimary(id)
            .address
            .toAddress()
    }
}

private fun AddressResponse.toAddress(): Address {
    return Address(
        id = id,
        recipientName = recipientName,
        phone = phone,
        address = address,
        city = city,
        province = province,
        postalCode = postalCode,
        isPrimary = isPrimary
    )
}