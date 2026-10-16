package com.example.tanimart.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.account.state.AccountViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tanimart.ui.account.ui.AccountFeedback
import com.example.tanimart.ui.account.ui.AccountHeader
import com.example.tanimart.ui.account.ui.AccountInfoRow
import com.example.tanimart.ui.account.ui.AccountSectionTitle
import com.example.tanimart.ui.account.ui.ChangePasswordDialog
import com.example.tanimart.ui.account.ui.EditNameDialog
import com.example.tanimart.ui.account.ui.PrimaryAddressSection

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
    onBack: () -> Unit,
    onChangePrimaryAddress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var showNameDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showPasswordDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.loadAccount()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Kembali"
                )
            }

            Column(Modifier.weight(1f)) {
                Text(
                    "Informasi Akun",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Kelola informasi pribadi Anda",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { viewModel.loadAccount() }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Muat ulang"
                )
            }
        }

        uiState.error?.let {
            AccountFeedback(text = it, isError = true)
        }

        uiState.message?.let {
            AccountFeedback(text = it, isError = false)
        }

        if (uiState.isLoading && uiState.account == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        val account = uiState.account

        if (account != null) {
            val user = account.user

            AccountHeader(
                name = user.name,
                email = user.email
            )

            AccountSectionTitle("Data Pribadi")

            AccountInfoRow(
                label = "Nama Lengkap",
                value = user.name,
                onEdit = {
                    viewModel.clearFeedback()
                    showNameDialog = true
                }
            )

            AccountInfoRow(
                label = "Email",
                value = user.email
            )

            AccountInfoRow(
                label = "Status Pengguna",
                value = user.status
            )

            AccountInfoRow(
                label = "Role",
                value = user.role
            )

            AccountSectionTitle("Alamat Utama")

            val address = account.primary_address

            if (address != null) {
                PrimaryAddressSection(
                    recipient = address.nama_penerima,
                    address = buildString {
                        append(address.alamat)
                        append(", ")
                        append(address.kota)
                        append(", ")
                        append(address.provinsi)
                        append(" ")
                        append(address.kode_pos)
                        append("\nTelepon: ")
                        append(address.no_telepon)
                    }
                )
                OutlinedButton(
                    onClick = onChangePrimaryAddress,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Ubah Alamat Utama")
                }
            } else {
                Text(
                    "Belum ada alamat utama. Tambahkan alamat melalui menu Alamat.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AccountSectionTitle("Keamanan")

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.clearFeedback()
                    showPasswordDialog = true
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            "Kata Sandi",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Perbarui kata sandi akun Anda",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text("Ubah")
                }
            }
        }
    }

    if (showNameDialog) {
        EditNameDialog(
            initialName = uiState.account?.user?.name.orEmpty(),
            isSaving = uiState.isSaving,
            onDismiss = { showNameDialog = false },
            onSave = { name ->
                viewModel.updateName(name)
                // Dialog tetap terbuka selama proses penyimpanan.
                // Tutup setelah berhasil, melalui pesan sukses.
            }
        )
    }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            isSaving = uiState.isSaving,
            onDismiss = { showPasswordDialog = false },
            onSave = { current, new, confirmation ->
                viewModel.updatePassword(
                    currentPassword = current,
                    newPassword = new,
                    confirmation = confirmation
                )
            }
        )
    }

    LaunchedEffect(uiState.message) {
        if (uiState.message != null) {
            showNameDialog = false
            showPasswordDialog = false
        }
    }
}
