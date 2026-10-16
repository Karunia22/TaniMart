package com.example.tanimart.ui.address.satate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.address.AddressRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.ui.address.data.Address
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddressUiState(
    val addresses: List<Address> = emptyList(),
    val selectedAddress: Address? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AddressViewModel : ViewModel() {

    private val repository = AddressRepository(
        RetrofitClient.addressApi
    )

    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState.asStateFlow()

    fun loadAddresses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val addresses = repository.getAddresses()

                val primaryAddress = addresses
                    .find { it.isPrimary }

                _uiState.value = _uiState.value.copy(
                    addresses = addresses,
                    selectedAddress = primaryAddress,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal mengambil alamat"
                )
            }
        }
    }

    fun selectAddress(address: Address) {
        _uiState.value = _uiState.value.copy(
            selectedAddress = address
        )
    }

    fun addAddress(
        recipientName: String,
        phone: String,
        address: String,
        city: String,
        province: String,
        postalCode: String,
        isPrimary: Boolean = false,
        onSuccess: () -> Unit = {}
    ) {
        Log.d("AddressViewModel", "addAddress() dipanggil")

        viewModelScope.launch {
            Log.d("AddressViewModel", "Coroutine dimulai")

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                Log.d("AddressViewModel", "Memanggil repository")

                val newAddress = repository.addAddress(
                    recipientName = recipientName,
                    phone = phone,
                    address = address,
                    city = city,
                    province = province,
                    postalCode = postalCode,
                    isPrimary = isPrimary
                )

                Log.d(
                    "AddressViewModel",
                    "Berhasil menambah alamat: ${newAddress.id}"
                )

                val updatedAddresses =
                    _uiState.value.addresses + newAddress

                _uiState.value = _uiState.value.copy(
                    addresses = updatedAddresses,
                    selectedAddress = newAddress,
                    isLoading = false
                )

                onSuccess()

            } catch (e: Exception) {
                Log.e(
                    "AddressViewModel",
                    "Gagal menambahkan alamat",
                    e
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal menambahkan alamat"
                )
            }
        }
    }

    fun updateAddress(
        id: Int,
        recipientName: String,
        phone: String,
        address: String,
        city: String,
        province: String,
        postalCode: String,
        isPrimary: Boolean = false,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val updatedAddress = repository.updateAddress(
                    id = id,
                    recipientName = recipientName,
                    phone = phone,
                    address = address,
                    city = city,
                    province = province,
                    postalCode = postalCode,
                    isPrimary = isPrimary
                )

                val updatedAddresses =
                    _uiState.value.addresses.map {
                        if (it.id == id) updatedAddress else it
                    }

                _uiState.value = _uiState.value.copy(
                    addresses = updatedAddresses,
                    selectedAddress =
                        if (_uiState.value.selectedAddress?.id == id) {
                            updatedAddress
                        } else {
                            _uiState.value.selectedAddress
                        },
                    isLoading = false
                )

                onSuccess()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal memperbarui alamat"
                )
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Gagal menambahkan alamat", e)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal menambahkan alamat"
                )
            }
        }
    }

    fun deleteAddress(
        id: Int,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                repository.deleteAddress(id)

                val updatedAddresses =
                    _uiState.value.addresses.filterNot {
                        it.id == id
                    }

                val selectedAddress =
                    if (_uiState.value.selectedAddress?.id == id) {
                        updatedAddresses.find { it.isPrimary }
                    } else {
                        _uiState.value.selectedAddress
                    }

                _uiState.value = _uiState.value.copy(
                    addresses = updatedAddresses,
                    selectedAddress = selectedAddress,
                    isLoading = false
                )

                onSuccess()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal menghapus alamat"
                )
            }
        }
    }

    fun setPrimary(
        id: Int,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val primaryAddress =
                    repository.setPrimary(id)

                val updatedAddresses =
                    _uiState.value.addresses.map {
                        if (it.id == id) {
                            primaryAddress
                        } else {
                            it.copy(isPrimary = false)
                        }
                    }

                _uiState.value = _uiState.value.copy(
                    addresses = updatedAddresses,
                    selectedAddress = primaryAddress,
                    isLoading = false
                )

                onSuccess()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Gagal mengubah alamat utama"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}