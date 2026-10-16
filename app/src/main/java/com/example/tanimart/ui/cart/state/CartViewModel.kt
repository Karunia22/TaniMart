package com.example.tanimart.ui.cart.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.cart.CartRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.cart.data.CartState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CartViewModel(
    private val repository: CartRepository = RetrofitClient.cartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    init {
        loadCart()
    }

    fun loadCart() {
        executeRequest {
            refreshCart()
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        if (quantity <= 0) {
            _errorMessage.value = "Jumlah produk harus lebih dari 0."
            return
        }

        if (product.stock <= 0) {
            _errorMessage.value = "Stok produk sedang habis."
            return
        }

        executeRequest {
            refreshCart(
                repository.addItem(
                    productId = product.id,
                    quantity = quantity
                )
            )
            _successMessage.value = "Produk berhasil ditambahkan ke keranjang."
        }
    }

    fun increaseQuantity(productId: Int) {
        val item = findItem(productId) ?: return

        if (item.quantity >= item.product.stock) {
            _errorMessage.value = "Jumlah sudah mencapai stok yang tersedia."
            return
        }

        updateQuantityOnServer(item, item.quantity + 1)
    }

    fun decreaseQuantity(productId: Int) {
        val item = findItem(productId) ?: return

        if (item.quantity <= 1) {
            removeFromCart(productId)
            return
        }

        updateQuantityOnServer(item, item.quantity - 1)
    }

    private fun updateQuantityOnServer(
        item: CartItem,
        newQuantity: Int
    ) {
        val cartItemId = item.id

        if (cartItemId == null) {
            _errorMessage.value = "ID item keranjang tidak ditemukan. Muat ulang keranjang."
            return
        }

        executeRequest {
            refreshCart(
                repository.updateQuantity(
                    cartItemId = cartItemId,
                    quantity = newQuantity
                )
            )
            _successMessage.value = "Jumlah produk berhasil diperbarui."
        }
    }

    fun removeFromCart(productId: Int) {
        val item = findItem(productId) ?: return
        val cartItemId = item.id

        if (cartItemId == null) {
            _errorMessage.value = "ID item keranjang tidak ditemukan. Muat ulang keranjang."
            return
        }

        executeRequest {
            refreshCart(repository.deleteItem(cartItemId))
            _successMessage.value = "Produk berhasil dihapus dari keranjang."
        }
    }

    fun clearCart() {
        executeRequest {
            refreshCart(repository.clearCart())
            _successMessage.value = "Keranjang berhasil dikosongkan."
        }
    }

    fun toggleSelection(productId: Int) {
        val updatedItems = _cartState.value.items.map { item ->
            if (item.product.id == productId) {
                item.copy(selected = !item.selected)
            } else {
                item
            }
        }

        _cartState.value = CartState(items = updatedItems)
    }

    fun selectAll(selected: Boolean) {
        val updatedItems = _cartState.value.items.map { item ->
            item.copy(selected = selected)
        }

        _cartState.value = CartState(items = updatedItems)
    }

    fun getSelectedItems(): List<CartItem> {
        return _cartState.value.items.filter { it.selected }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }

    private fun findItem(productId: Int): CartItem? {
        return _cartState.value.items.firstOrNull {
            it.product.id == productId
        }
    }

    private suspend fun refreshCart(
        refreshedItems: List<CartItem>? = null
    ) {
        val previousSelection = _cartState.value.items
            .associate { it.product.id to it.selected }

        val serverItems = refreshedItems ?: repository.getCart()

        val mappedItems = serverItems.map { item ->
            item.copy(
                selected = previousSelection[item.product.id] ?: true
            )
        }

        _cartState.value = CartState(items = mappedItems)
    }

    private fun executeRequest(
        request: suspend () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                request()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: HttpException) {
                _errorMessage.value = when (exception.code()) {
                    401 -> "Sesi login berakhir. Silakan login kembali."
                    403 -> "Kamu tidak memiliki akses ke item ini."
                    404 -> "Produk atau item keranjang tidak ditemukan."
                    422 -> "Permintaan ditolak. Periksa stok dan jumlah produk."
                    else -> "Terjadi kesalahan dari server (${exception.code()})."
                }
            } catch (exception: IOException) {
                _errorMessage.value =
                    "Tidak dapat terhubung ke server. Periksa koneksi jaringan."
            } catch (exception: Exception) {
                _errorMessage.value =
                    exception.message ?: "Terjadi kesalahan yang tidak diketahui."
            } finally {
                _isLoading.value = false
            }
        }
    }
}