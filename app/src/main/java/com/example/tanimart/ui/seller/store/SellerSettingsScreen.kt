package com.example.tanimart.ui.seller.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.components.TaniMartButton
import com.example.tanimart.ui.components.TaniMartTextField
import com.example.tanimart.ui.seller.store.state.SellerStoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerSettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SellerStoreViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Toko") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Informasi Toko",
                style = MaterialTheme.typography.titleLarge
            )

            TaniMartTextField(
                value = state.namaToko,
                onValueChange = viewModel::onNamaTokoChange,
                label = "Nama Toko",
                placeholder = "Contoh: Tani Makmur"
            )

            TaniMartTextField(
                value = state.deskripsi,
                onValueChange = viewModel::onDeskripsiChange,
                label = "Deskripsi Toko",
                placeholder = "Deskripsi singkat toko"
            )

            TaniMartTextField(
                value = state.alamatUsaha,
                onValueChange = viewModel::onAlamatUsahaChange,
                label = "Alamat Usaha",
                placeholder = "Alamat lengkap usaha"
            )

            TaniMartTextField(
                value = state.noTelepon,
                onValueChange = viewModel::onNoTeleponChange,
                label = "Nomor Telepon",
                placeholder = "08xxxxxxxxxx"
            )

            if (state.successMessage != null) {
                Text(
                    text = state.successMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TaniMartButton(
                text = if (state.isSaving) "Menyimpan..." else "Simpan",
                onClick = viewModel::save,
                enabled = !state.isSaving
            )
        }
    }
}
