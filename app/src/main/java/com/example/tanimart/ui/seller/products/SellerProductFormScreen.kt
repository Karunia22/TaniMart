package com.example.tanimart.ui.seller.products

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.seller.products.components.ProductFormActions
import com.example.tanimart.ui.seller.products.components.ProductFormFields
import com.example.tanimart.ui.seller.products.components.ProductImagePicker
import com.example.tanimart.ui.seller.products.state.CreateProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProductFormScreen(
    onBackClick: () -> Unit,
    onProductCreated: () -> Unit,
    productId: Int? = null,
    viewModel: CreateProductViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var imageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProductForEdit(productId)
        } else {
            viewModel.resetState()
        }
    }

    LaunchedEffect(state.product) {
        state.product?.let { product ->
            name = product.name
            description = product.description.orEmpty()
            price = product.price.toString()
            stock = product.stock.toString()
            weight = product.weight.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (productId == null) "Tambah Produk" else "Edit Produk"
                    )                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        enabled = !state.isSaving
                    ) {
                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            ProductImagePicker(
                selectedImages = imageUris,
                onImagesSelected = { imageUris = it }
            )

            ProductFormFields(
                name = name,
                onNameChange = {
                    name = it
                    errorMessage = null
                },
                description = description,
                onDescriptionChange = {
                    description = it
                },
                price = price,
                onPriceChange = {
                    price = it
                    errorMessage = null
                },
                stock = stock,
                onStockChange = {
                    stock = it
                    errorMessage = null
                },
                weight = weight,
                onWeightChange = {
                    weight = it
                    errorMessage = null
                }
            )

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.message?.let { message ->
                Text(
                    text = message,
                    color = if (state.isSuccess) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }

            ProductFormActions(
                isSaving = state.isSaving,
                onCancel = onBackClick,
                onSave = {
                    if (state.isSaving) {
                        return@ProductFormActions
                    }

                    val parsedPrice = price.toDoubleOrNull()
                    val parsedStock = stock.toIntOrNull()
                    val parsedWeight = weight.toIntOrNull()

                    errorMessage = when {
                        name.isBlank() ->
                            "Nama produk wajib diisi."

                        parsedPrice == null ||
                                !parsedPrice.isFinite() ||
                                parsedPrice <= 0 ->
                            "Harga harus lebih dari nol."

                        parsedStock == null || parsedStock < 0 ->
                            "Stok tidak valid."

                        parsedWeight == null || parsedWeight <= 0 ->
                            "Berat harus lebih dari nol."

                        imageUris.isEmpty() && productId == null ->
                            "Pilih gambar produk terlebih dahulu."

                        else -> null
                    }

                    if (errorMessage == null) {
                        if (productId == null) {
                            viewModel.createProduct(
                                name = name.trim(),
                                description = description.trim(),
                                price = parsedPrice!!,
                                stock = parsedStock!!,
                                weight = parsedWeight!!,
                                imageUris = imageUris
                            )
                        } else {
                            viewModel.updateProduct(
                                productId = productId,
                                name = name.trim(),
                                description = description.trim(),
                                price = parsedPrice!!,
                                stock = parsedStock!!,
                                weight = parsedWeight!!
                            )
                        }
                    }
                }
            )
        }
    }
}