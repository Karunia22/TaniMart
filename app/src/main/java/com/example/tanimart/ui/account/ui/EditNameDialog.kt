package com.example.tanimart.ui.account.ui

//package com.example.tanimart.ui.account

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun EditNameDialog(
    initialName: String,
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var name by rememberSaveable(initialName) {
        mutableStateOf(initialName)
    }

    AlertDialog(
        onDismissRequest = {
            if (!isSaving) onDismiss()
        },
        title = { Text("Ubah Nama") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama lengkap") },
                singleLine = true,
                enabled = !isSaving
            )
        },
        confirmButton = {
            Button(
                enabled = !isSaving && name.isNotBlank(),
                onClick = { onSave(name.trim()) }
            ) {
                Text(if (isSaving) "Menyimpan..." else "Simpan")
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
