package com.example.tanimart.ui.seller.orders.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tanimart.data.order.OrderRepository

class SellerOrdersViewModelFactory(
    private val repository: OrderRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
    ): T {
        if (modelClass.isAssignableFrom(SellerOrdersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SellerOrdersViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}",
        )
    }
}