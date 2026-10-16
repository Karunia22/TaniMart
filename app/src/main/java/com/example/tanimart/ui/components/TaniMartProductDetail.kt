package com.example.tanimart.ui.components

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tanimart.data.product.Product

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaniMartProductDetail(
    product: Product,
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProductImageCarousel(images = product.images)


        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = formatRupiah(product.price),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Berat",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "${product.weight} gram",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Stok",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = if (product.stock > 0) {
                            "${product.stock} tersedia"
                        } else {
                            "Stok habis"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (product.stock > 0) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            }

            product.sellerName
                ?.takeIf { it.isNotBlank() }
                ?.let { sellerName ->
                    Text(
                        text = "Penjual: $sellerName",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }


        }

        QuantitySelector(
            quantity = quantity,
            stock = product.stock,
            onDecrease = onDecrease,
            onIncrease = onIncrease
        )
        Spacer(Modifier.height(15.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Deskripsi Produk",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = product.description
                    ?.takeIf { it.isNotBlank() }
                    ?: "Deskripsi produk belum tersedia.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductImageCarousel(
    images: List<String>
) {
    val pageCount = images.size.coerceAtLeast(1)

    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            ProductImage(
                imageUrl = images.getOrNull(page)
            )
        }

        if (images.size > 1) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { index ->
                    Text(
                        text = if (index == pagerState.currentPage) "●" else "○",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductImage(
    imageUrl: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 10.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNullOrBlank()) {
                Text(
                    text = "Foto belum tersedia",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Foto produk",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    stock: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Jumlah",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            onClick = onDecrease,
            modifier = Modifier.size(44.dp),
            contentPadding = PaddingValues(0.dp),
            enabled = quantity > 1
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "−",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Box(
            modifier = Modifier.width(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        OutlinedButton(
            onClick = onIncrease,
            modifier = Modifier.size(44.dp),
            contentPadding = PaddingValues(0.dp),
            enabled = quantity < stock
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
