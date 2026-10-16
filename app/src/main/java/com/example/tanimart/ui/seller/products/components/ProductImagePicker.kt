package com.example.tanimart.ui.seller.products.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProductImagePicker(
    selectedImages: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(10)
    ) { uris ->
        // Foto yang dipilih menggantikan pilihan sebelumnya.
        onImagesSelected(uris)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Foto Produk",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Pilih maksimal 10 foto. Foto pertama menjadi foto utama.",
            style = MaterialTheme.typography.bodySmall
        )

        if (selectedImages.isEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Belum ada foto produk.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                selectedImages.forEachIndexed { index, uri ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Foto produk ${index + 1}",
                            modifier = Modifier
                                .size(width = 150.dp, height = 150.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = if (index == 0) {
                                "Foto utama"
                            } else {
                                "Foto ${index + 1}"
                            },
                            style = MaterialTheme.typography.bodySmall
                        )

                        OutlinedButton(
                            onClick = {
                                onImagesSelected(
                                    selectedImages.filterIndexed { i, _ ->
                                        i != index
                                    }
                                )
                            }
                        ) {
                            Text("Hapus")
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (selectedImages.isEmpty()) {
                    "Pilih Foto"
                } else {
                    "Pilih Ulang Foto"
                }
            )
        }
    }
}