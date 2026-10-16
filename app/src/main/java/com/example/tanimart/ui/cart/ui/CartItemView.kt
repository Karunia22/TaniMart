
package com.example.tanimart.ui.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.components.formatRupiah

@Composable
fun CartItemView(
    item: CartItem,
    onSelectionChange: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val price = formatRupiah(item.product.price)

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.selected,
                onCheckedChange = { onSelectionChange() }
            )

            Spacer(modifier = Modifier.width(6.dp))

            AsyncImage(
                model = item.product.imageUrl,
                contentDescription = "Gambar ${item.product.name}",
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Text(
                            text = "−",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.width(28.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus produk"
                )
            }
        }
    }
}