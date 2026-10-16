package com.example.tanimart.ui.address.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.components.TaniMartTextField

@Composable
fun AddressForm(
    recipientName: String,
    phone: String,
    address: String,
    city: String,
    province: String,
    postalCode: String,
    submitLabel: String,
    onRecipientNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onProvinceChange: (String) -> Unit,
    onPostalCodeChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TaniMartTextField(
            value = recipientName,
            onValueChange = onRecipientNameChange,
            label = "Nama penerima"
        )

        TaniMartTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = "Nomor telepon"
        )

        TaniMartTextField(
            value = address,
            onValueChange = onAddressChange,
            label = "Alamat lengkap"
        )

        TaniMartTextField(
            value = city,
            onValueChange = onCityChange,
            label = "Kota"
        )

        TaniMartTextField(
            value = province,
            onValueChange = onProvinceChange,
            label = "Provinsi"
        )

        TaniMartTextField(
            value = postalCode,
            onValueChange = onPostalCodeChange,
            label = "Kode pos"
        )

//        Button(
//            onClick = onSubmit,
//            modifier = Modifier.fillMaxWidth(),
//            enabled =
//                recipientName.isNotBlank() &&
//                        phone.isNotBlank() &&
//                        address.isNotBlank() &&
//                        city.isNotBlank() &&
//                        province.isNotBlank() &&
//                        postalCode.isNotBlank()
//        ) {
//            Text(submitLabel)
//        }
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(submitLabel)
        }

    }
}