package com.example.tanimart.ui.address.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.address.data.Address
import com.example.tanimart.ui.address.satate.AddressViewModel

@Composable
fun EditAddressScreen(
    address: Address,
    viewModel: AddressViewModel,
    onBackClick: () -> Unit,
    onAddressUpdated: () -> Unit
) {
    var recipientName by remember {
        mutableStateOf(address.recipientName)
    }

    var phone by remember {
        mutableStateOf(address.phone)
    }

    var fullAddress by remember {
        mutableStateOf(address.address)
    }

    var city by remember {
        mutableStateOf(address.city)
    }

    var province by remember {
        mutableStateOf(address.province)
    }

    var postalCode by remember {
        mutableStateOf(address.postalCode)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali"
                )
            }
        }

        item {
            Text(
                text = "Edit Alamat"
            )
        }

        item {
            AddressForm(
                recipientName = recipientName,
                phone = phone,
                address = fullAddress,
                city = city,
                province = province,
                postalCode = postalCode,
                submitLabel = "Simpan Perubahan",

                onRecipientNameChange = {
                    recipientName = it
                },

                onPhoneChange = {
                    phone = it
                },

                onAddressChange = {
                    fullAddress = it
                },

                onCityChange = {
                    city = it
                },

                onProvinceChange = {
                    province = it
                },

                onPostalCodeChange = {
                    postalCode = it
                },

                onSubmit = {
                    viewModel.updateAddress(
                        id = address.id,
                        recipientName = recipientName,
                        phone = phone,
                        address = fullAddress,
                        city = city,
                        province = province,
                        postalCode = postalCode,
                        onSuccess = onAddressUpdated
                    )
                }
            )
        }
    }
}