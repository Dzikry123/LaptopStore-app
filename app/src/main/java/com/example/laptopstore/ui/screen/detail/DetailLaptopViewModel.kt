package com.example.laptopstore.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laptopstore.ui.common.UiState
import com.example.laptopstore.data.LaptopRepository
import com.example.laptopstore.model.Laptop
import com.example.laptopstore.model.OrderLaptop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailLaptopViewModel(
    private val repository: LaptopRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderLaptop>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderLaptop>>
        get() = _uiState

    fun getLaptopById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderLaptopById(rewardId))
        }
    }

    fun addToCart(laptop: Laptop, count: Int) {
        viewModelScope.launch {
            repository.updateOrderLaptop(laptop.id, count)
        }
    }
}