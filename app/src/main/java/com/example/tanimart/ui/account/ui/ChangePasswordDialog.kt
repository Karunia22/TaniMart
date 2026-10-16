package com.example.tanimart.ui.account.ui


//package com.example.tanimart.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordDialog(
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var currentPassword by rememberSaveable {
        mutableStateOf("")
    }
    var newPassword by rememberSaveable {
        mutableStateOf("")
    }
    var confirmation by rememberSaveable {
        mutableStateOf("")
    }

    var showCurrentPassword by remember {
        mutableStateOf(false)
    }

    var showNewPassword by remember {
        mutableStateOf(false)
    }

    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = {
            if (!isSaving) onDismiss()
        },
        title = { Text("Ubah Kata Sandi") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Kata Sandi Saat Ini") },
                    singleLine = true,
                    visualTransformation = if (showCurrentPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showCurrentPassword = !showCurrentPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showCurrentPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showCurrentPassword) {
                                    "Sembunyikan kata sandi"
                                } else {
                                    "Tampilkan kata sandi"
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Kata Sandi Saat Ini") },
                    singleLine = true,
                    visualTransformation = if (showNewPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showNewPassword = !showNewPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showNewPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showNewPassword) {
                                    "Sembunyikan kata sandi"
                                } else {
                                    "Tampilkan kata sandi"
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )


                OutlinedTextField(
                    value = confirmation,
                    onValueChange = { confirmation = it },
                    label = { Text("Kata Sandi Saat Ini") },
                    singleLine = true,
                    visualTransformation = if (showConfirmPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showConfirmPassword = !showConfirmPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showConfirmPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showConfirmPassword) {
                                    "Sembunyikan kata sandi"
                                } else {
                                    "Tampilkan kata sandi"
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )


            }
        },
        confirmButton = {
            Button(
                enabled = !isSaving &&
                        currentPassword.isNotBlank() &&
                        newPassword.length >= 8 &&
                        confirmation.isNotBlank(),
                onClick = {
                    onSave(
                        currentPassword,
                        newPassword,
                        confirmation
                    )
                }
            ) {
                Text(if (isSaving) "Memproses..." else "Simpan")
            }
        },
        dismissButton = {
            TextButton(
                enabled = !isSaving,
                onClick = onDismiss
            ) {
                Text("Batal")
            }
        }
    )
}
