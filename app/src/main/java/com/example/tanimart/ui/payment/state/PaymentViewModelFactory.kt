package com.example.tanimart.ui.payment.state


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tanimart.data.payment.PaymentRepository

class PaymentViewModelFactory(
    private val repository: PaymentRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}
