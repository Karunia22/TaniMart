package com.example.tanimart.ui.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.ui.address.satate.AddressViewModel
import com.example.tanimart.ui.address.ui.AddressItem


@Composable
fun AddressListScreen(
    viewModel: AddressViewModel,
    onBackClick: () -> Unit,
    onAddressSelected: () -> Unit,
    onAddAddressClick: () -> Unit,
    onEditAddressClick: (Int) -> Unit,
    selectPrimaryMode: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.loadAddresses()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali"
                )
            }

            Text(
                text = if (selectPrimaryMode) {
                    "Ubah Alamat Utama"
                } else {
                    "Pilih Alamat"
                },
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tombol tambah alamat
        Button(
            onClick = onAddAddressClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Text("Tambah Alamat")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading di tengah layar
        if (uiState.isLoading && uiState.addresses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = uiState.addresses,
                    key = { it.id }
                ) { address ->
                    AddressItem(
                        address = address,
                        selected = address.id == uiState.selectedAddress?.id,

                        onClick = {
                            if (selectPrimaryMode) {
                                viewModel.setPrimary(address.id) {
                                    onAddressSelected()
                                }
                            } else {
                                viewModel.selectAddress(address)
                                onAddressSelected()
                            }
                        },

                        onEditClick = {
                            onEditAddressClick(address.id)
                        },

                        onDeleteClick = {
                            viewModel.deleteAddress(address.id)
                        },

                        onSetPrimaryClick = {
                            viewModel.setPrimary(address.id) {
                                if (selectPrimaryMode) {
                                    onAddressSelected()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}