package com.example.tanimart.ui.address

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.address.satate.AddressViewModel
import com.example.tanimart.ui.address.ui.AddressForm

@Composable
fun AddAddressScreen(
    viewModel: AddressViewModel,
    onBackClick: () -> Unit,
    onAddressAdded: () -> Unit
) {
    var recipientName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

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
                text = "Tambah Alamat"
            )
        }

        item {
            AddressForm(
                recipientName = recipientName,
                phone = phone,
                address = address,
                city = city,
                province = province,
                postalCode = postalCode,
                submitLabel = "Simpan Alamat",

                onRecipientNameChange = {
                    recipientName = it
                },

                onPhoneChange = {
                    phone = it
                },

                onAddressChange = {
                    address = it
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
                    Log.d("AddAddress", "Tombol Simpan ditekan")

                    viewModel.addAddress(
                        recipientName = recipientName,
                        phone = phone,
                        address = address,
                        city = city,
                        province = province,
                        postalCode = postalCode,
                        onSuccess = onAddressAdded
                    )
                }
            )
        }
    }
}