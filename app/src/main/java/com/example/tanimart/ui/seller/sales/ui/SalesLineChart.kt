package com.example.tanimart.ui.seller.sales.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun SalesLineChart(
    labels: List<String>,
    values: List<Double>,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            if (values.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada data",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                return@Column
            }

            val maxValue = max(values.maxOrNull() ?: 0.0, 1.0)
            val primaryColor = MaterialTheme.colorScheme.primary

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height
                    val count = values.size

                    if (count >= 2) {
                        for (i in 0 until count - 1) {
                            val x1 = width * (i.toFloat() / (count - 1).toFloat())
                            val y1 = height - (values[i].toFloat() / maxValue.toFloat()) * height
                            val x2 = width * ((i + 1).toFloat() / (count - 1).toFloat())
                            val y2 = height - (values[i + 1].toFloat() / maxValue.toFloat()) * height

                            drawLine(
                                color = primaryColor,
                                start = Offset(x1, y1),
                                end = Offset(x2, y2),
                                strokeWidth = 4f
                            )
                        }
                    }

                    values.forEachIndexed { index, _ ->
                        val x = if (count == 1) width / 2 else width * (index.toFloat() / (count - 1).toFloat())
                        val y = height - (values[index].toFloat() / maxValue.toFloat()) * height
                        drawCircle(
                            color = primaryColor,
                            radius = 6f,
                            center = Offset(x, y)
                        )
                    }
                }
            }

            if (labels.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val displayLabels = if (labels.size <= 4) labels else listOf(
                        labels.first(),
                        labels[labels.size / 2],
                        labels.last()
                    )
                    displayLabels.forEach { label ->
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
