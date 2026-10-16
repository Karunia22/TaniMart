
package com.example.tanimart.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.profile.data.ProfileMenu
import com.example.tanimart.ui.profile.data.ProfileUiState
import com.example.tanimart.ui.profile.ui.ProfileMenuItem

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onMenuClick: (ProfileMenu) -> Unit,
    onRetryClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text(
                text = "Profil",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            ProfileHeader(
                uiState = uiState,
                onRetryClick = onRetryClick
            )
        }

        item {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 8.dp
                )
            )
        }

        items(ProfileMenu.entries) { menu ->
            ProfileMenuItem(
                menu = menu,
                onClick = { onMenuClick(menu) }
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    uiState: ProfileUiState,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                bottom = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto profil",
                modifier = Modifier.padding(20.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
                Text(
                    text = "Memuat informasi akun...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

                TextButton(onClick = onRetryClick) {
                    Text("Coba Lagi")
                }
            }

            uiState.name.isNotBlank() &&
                    uiState.email.isNotBlank() -> {
                Text(
                    text = uiState.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = uiState.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> {
                Text(
                    text = "Informasi akun belum tersedia.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                TextButton(onClick = onRetryClick) {
                    Text("Muat Ulang")
                }
            }
        }
    }
}
