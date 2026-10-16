
package com.example.tanimart.ui.order.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.order.data.OrderFilter

@Composable
fun OrderFilterBar(
    selectedFilter: OrderFilter,
    onFilterSelected: (OrderFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OrderFilter.entries.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = {
                    onFilterSelected(filter)
                },
                label = {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}