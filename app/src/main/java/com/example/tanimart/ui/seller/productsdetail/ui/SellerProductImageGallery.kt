package com.example.tanimart.ui.seller.productsdetail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SellerProductImageGallery(
    imageUrls: List<String>,
    productName: String,
    modifier: Modifier = Modifier
) {
    val images = imageUrls.distinct()

    var selectedIndex by remember(images) {
        mutableIntStateOf(0)
    }

    val selectedImage = images.getOrNull(selectedIndex)

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = selectedImage,
            contentDescription = "Foto utama $productName",
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        if (images.size > 1) {
            Text(
                text = "Galeri Foto (${images.size})",
                style = MaterialTheme.typography.titleMedium
            )

            androidx.compose.foundation.layout.Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                ,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                images.forEachIndexed { index, imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Foto ${index + 1} $productName",
                        modifier = Modifier
                            .size(84.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                selectedIndex = index
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}