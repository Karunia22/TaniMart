package com.example.tanimart.ui.seller.products.state

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.product.Product
import com.example.tanimart.data.product.ProductRepository
import com.example.tanimart.ui.seller.products.data.CreateProductUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

class CreateProductViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ProductRepository(
        RetrofitClient.productApi
    )

    private val _uiState =
        MutableStateFlow(CreateProductUiState())

    val uiState = _uiState.asStateFlow()
    private var _loadedProduct: Product? = null

    val loadedProduct: Product?
        get() = _loadedProduct
    fun createProduct(
        name: String,
        description: String,
        price: Double,
        stock: Int,
        weight: Int,
        imageUris: List<Uri>
    ) {
        if (_uiState.value.isSaving) return

        if (imageUris.isEmpty()) {
            _uiState.value = CreateProductUiState(
                message = "Pilih gambar produk terlebih dahulu."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = CreateProductUiState(
                isSaving = true
            )

            val tempFiles = mutableListOf<File>()

            try {
                val app = getApplication<Application>()

                val imageParts = withContext(Dispatchers.IO) {
                    imageUris.mapIndexed { index, uri ->
                        val mimeType = app.contentResolver
                            .getType(uri) ?: "image/jpeg"

                        val extension = extensionFor(mimeType)

                        val tempFile = File.createTempFile(
                            "product_${index}_",
                            ".$extension",
                            app.cacheDir
                        )

                        try {
                            val input = app.contentResolver
                                .openInputStream(uri)
                                ?: throw IOException(
                                    "Foto ke-${index + 1} tidak dapat dibaca."
                                )

                            input.use { source ->
                                tempFile.outputStream().use { destination ->
                                    source.copyTo(destination)
                                }
                            }

                            tempFiles.add(tempFile)

                            val imageBody = tempFile.asRequestBody(
                                mimeType.toMediaTypeOrNull()
                            )

                            MultipartBody.Part.createFormData(
                                "images[]",
                                "product_image_${index + 1}.$extension",
                                imageBody
                            )
                        } catch (e: Exception) {
                            tempFile.delete()
                            throw e
                        }
                    }
                }

                fun textPart(value: String): RequestBody {
                    return value.toRequestBody(
                        "text/plain".toMediaTypeOrNull()
                    )
                }

                val response = repository.createProduct(
                    name = textPart(name.trim()),
                    description = textPart(description.trim()),
                    price = textPart(price.toString()),
                    stock = textPart(stock.toString()),
                    weight = textPart(weight.toString()),
                    status = textPart("aktif"),
                    images = imageParts
                )

                _uiState.value = CreateProductUiState(
                    isSuccess = true,
                    message = response.message
                )
            } catch (e: Exception) {
                _uiState.value = CreateProductUiState(
                    message = e.message
                        ?: "Gagal menambahkan produk."
                )
            } finally {
                withContext(Dispatchers.IO) {
                    tempFiles.forEach { it.delete() }
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = CreateProductUiState()
    }

    private fun extensionFor(mimeType: String): String {
        return when (mimeType.lowercase()) {
            "image/png" -> "png"
            "image/webp" -> "webp"
            "image/jpeg", "image/jpg" -> "jpg"
            else -> "jpg"
        }
    }

    fun loadProductForEdit(productId: Int) {
        viewModelScope.launch {
            _uiState.value = CreateProductUiState(
                isSaving = true,
                message = "Memuat produk..."
            )

            try {
                val product = repository.getProduct(productId)

                _uiState.value = CreateProductUiState(
                    product = product
                )
            } catch (e: Exception) {
                _uiState.value = CreateProductUiState(
                    message = e.message ?: "Gagal memuat produk."
                )
            }
        }
    }

    fun updateProduct(
        productId: Int,
        name: String,
        description: String,
        price: Double,
        stock: Int,
        weight: Int
    ) {
        if (_uiState.value.isSaving) return

        viewModelScope.launch {
            _uiState.value = CreateProductUiState(isSaving = true)

            try {
                val response = repository.updateProduct(
                    productId = productId,
                    fields = mapOf(
                        "nama_produk" to name.trim(),
                        "deskripsi" to description.trim(),
                        "harga" to price.toString(),
                        "stok" to stock.toString(),
                        "berat" to weight.toString()
                    )
                )

                _uiState.value = CreateProductUiState(
                    isSuccess = true,
                    message = response.message
                )
            } catch (e: Exception) {
                _uiState.value = CreateProductUiState(
                    message = e.message ?: "Gagal memperbarui produk."
                )
            }
        }
    }
}