package com.example.tanimart.ui.seller.sales

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.seller.ReportDownloader
import com.example.tanimart.data.seller.SalesSummaryDto
import com.example.tanimart.ui.components.formatRupiah
import com.example.tanimart.ui.order.data.toOrderStatus
import com.example.tanimart.ui.seller.sales.state.SellerSalesViewModel
import com.example.tanimart.ui.seller.sales.ui.SalesLineChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerSalesScreen(
    onBackClick: () -> Unit,
    viewModel: SellerSalesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    fun downloadReport(period: String, year: Int? = null) {
        val token = com.example.tanimart.data.network.TokenStorage.getToken() ?: ""
        val base = "http://192.168.1.91:8000/api/seller/sales/report"
        val url = buildString {
            append(base)
            append("?period=").append(period)
            if (year != null) append("&year=").append(year)
        }
        val filename = "laporan-penjualan-$period-${System.currentTimeMillis()}.pdf"
        ReportDownloader.downloadPdf(context, url, token, filename)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Penjualan") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                state.errorMessage != null -> {
                    Text(text = state.errorMessage.orEmpty(), color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = viewModel::loadAll) { Text("Coba Lagi") }
                }
                else -> {
                    state.summary?.let { SalesSummaryContent(summary = it) }
                    PeriodSelector(
                        selected = state.selectedPeriod,
                        onSelect = { p: String -> viewModel.selectPeriod(p) }
                    )
                    if (state.isLoadingChart) {
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val chart = state.chart
                        SalesLineChart(labels = chart?.daily?.labels ?: emptyList(), values = chart?.daily?.revenues ?: emptyList(), title = "Grafik Penjualan (Harian)")
                        SalesLineChart(labels = chart?.monthly?.labels ?: emptyList(), values = chart?.monthly?.revenues ?: emptyList(), title = "Grafik Penjualan (Bulanan)")
                        SalesLineChart(labels = chart?.yearly?.labels ?: emptyList(), values = chart?.yearly?.revenues ?: emptyList(), title = "Grafik Penjualan (Tahunan)")
                    }
                    FilledTonalButton(onClick = { downloadReport("monthly") }, modifier = Modifier.fillMaxWidth()) { Text("Laporan Bulanan (PDF)") }
                    OutlinedButton(onClick = { downloadReport("yearly", year = java.time.LocalDate.now().year) }, modifier = Modifier.fillMaxWidth()) { Text("Laporan Tahunan (PDF)") }
                }
            }
        }
    }
}

@Composable
private fun PeriodSelector(
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("7d", "30d", "90d", "1y").forEach { period ->
            FilterChip(
                selected = selected == period,
                onClick = { onSelect(period) },
                label = { Text(periodLabel(period)) }
            )
        }
    }
}

private fun periodLabel(period: String): String = when (period) {
    "7d" -> "7 Hari"
    "30d" -> "30 Hari"
    "90d" -> "90 Hari"
    "1y" -> "1 Tahun"
    else -> period
}

@Composable
private fun SalesSummaryContent(summary: com.example.tanimart.data.seller.SalesSummaryDto) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Total Pendapatan", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = formatRupiah(summary.totalPendapatan), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatCard(title = "Total Pesanan", value = summary.totalPesanan.toString(), modifier = Modifier.weight(1f))
        StatCard(title = "Produk Terjual", value = summary.totalProdukTerjual.toString(), modifier = Modifier.weight(1f))
    }
    if (summary.pesananPerStatus.isNotEmpty()) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Pesanan per Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                summary.pesananPerStatus.forEach { (status, total) ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = status.toOrderStatus().label, style = MaterialTheme.typography.bodyMedium)
                        Text(text = total.toString(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}
