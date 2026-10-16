package com.example.tanimart.ui.seller.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.seller.data.SellerDashboardMenu

@Composable
fun SellerDashboardMenuSection(
    onMenuClick: (SellerDashboardMenu) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Menu Pengelolaan",
            style = MaterialTheme.typography.titleLarge
        )

        SellerDashboardMenu.entries
            .toList()
            .chunked(2)
            .forEach { rowMenus ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowMenus.forEach { menu ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onMenuClick(menu)
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = menu.icon,
                                    contentDescription = menu.title,
                                    tint = MaterialTheme.colorScheme.primary
                                )

                                Text(
                                    text = menu.title,
                                    style =
                                        MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = menu.description,
                                    style =
                                        MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme
                                        .onSurfaceVariant
                                )
                            }
                        }
                    }

                    if (rowMenus.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
    }
}