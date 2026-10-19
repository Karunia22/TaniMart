package com.example.tanimart.ui.seller.application

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
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.components.TaniMartButton
import com.example.tanimart.ui.components.TaniMartTextField
import com.example.tanimart.ui.seller.application.data.SellerApplicationStatus
import com.example.tanimart.ui.seller.application.data.SellerApplicationUiModel
import com.example.tanimart.ui.seller.application.state.SellerRegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerRegisterScreen(
    onBackClick: () -> Unit,
    onOpenDashboard: () -> Unit,
    viewModel: SellerRegisterViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Jadi Penjual") },
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val application = state.existingApplication

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Memuat data pengajuan...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                application != null -> {
                    SellerApplicationStatusContent(
                        application = application,
                        onOpenDashboard = onOpenDashboard,
                        onNewApplication = viewModel::startNewApplication
                    )
                }

                else -> {
                    Text(
                        text = "Daftar Jadi Penjual",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Lengkapi data toko Anda. Pengajuan akan ditinjau " +
                            "oleh admin sebelum toko aktif.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    TaniMartTextField(
                        value = state.namaToko,
                        onValueChange = viewModel::onNamaTokoChange,
                        label = "Nama Toko",
                        placeholder = "Contoh: Tani Makmur"
                    )

                    TaniMartTextField(
                        value = state.deskripsiToko,
                        onValueChange = viewModel::onDeskripsiTokoChange,
                        label = "Deskripsi Toko (opsional)",
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

                    Spacer(modifier = Modifier.height(8.dp))

                    TaniMartButton(
                        text = if (state.isSubmitting) {
                            "Mengirim..."
                        } else {
                            "Kirim Pengajuan"
                        },
                        onClick = viewModel::submit,
                        enabled = !state.isSubmitting
                    )
                }
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SellerApplicationStatusContent(
    application: SellerApplicationUiModel,
    onOpenDashboard: () -> Unit,
    onNewApplication: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = application.namaToko,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = application.alamatUsaha,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = application.noTelepon,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = statusLabel(application.status),
                style = MaterialTheme.typography.titleMedium,
                color = statusColor(application.status),
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = when (application.status) {
                    SellerApplicationStatus.PENDING ->
                        "Pengajuan Anda sedang menunggu peninjauan admin."

                    SellerApplicationStatus.APPROVED ->
                        "Selamat! Toko Anda sudah aktif."

                    SellerApplicationStatus.REJECTED ->
                        "Pengajuan Anda ditolak. Silakan ajukan ulang."

                    SellerApplicationStatus.UNKNOWN ->
                        "Status pengajuan Anda sedang diproses."
                },
                style = MaterialTheme.typography.bodyMedium
            )

            if (!application.catatanAdmin.isNullOrBlank()) {
                Text(
                    text = "Catatan admin: ${application.catatanAdmin}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (application.status) {
                SellerApplicationStatus.APPROVED -> {
                    TaniMartButton(
                        text = "Buka Dashboard Penjual",
                        onClick = onOpenDashboard
                    )
                }

                SellerApplicationStatus.REJECTED -> {
                    TaniMartButton(
                        text = "Ajukan Ulang",
                        onClick = onNewApplication
                    )
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun statusLabel(status: SellerApplicationStatus): String {
    return when (status) {
        SellerApplicationStatus.PENDING -> "Menunggu Peninjauan"
        SellerApplicationStatus.APPROVED -> "Disetujui"
        SellerApplicationStatus.REJECTED -> "Ditolak"
        SellerApplicationStatus.UNKNOWN -> "Tidak diketahui"
    }
}

@Composable
private fun statusColor(status: SellerApplicationStatus) = when (status) {
    SellerApplicationStatus.APPROVED -> MaterialTheme.colorScheme.primary
    SellerApplicationStatus.REJECTED -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.onSurface
}
