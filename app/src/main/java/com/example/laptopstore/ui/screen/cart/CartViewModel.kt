package com.example.laptopstore.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laptopstore.ui.common.UiState
import com.example.laptopstore.data.LaptopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: LaptopRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderLaptops() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderLaptops()
                .collect { orderLaptop ->
                    val totalRequiredPoint =
                        orderLaptop.sumOf { it.laptop.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderLaptop, totalRequiredPoint))
                }
        }
    }

    fun updateOrderReward(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderLaptop(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderLaptops()
                    }
                }
        }
    }
}