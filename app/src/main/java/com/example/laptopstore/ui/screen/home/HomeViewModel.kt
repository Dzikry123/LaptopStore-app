package com.example.laptopstore.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laptopstore.ui.common.UiState
import com.example.laptopstore.data.LaptopRepository
import com.example.laptopstore.model.OrderLaptop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: LaptopRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderLaptop>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderLaptop>>>
        get() = _uiState

    fun getAllLaptops() {
        viewModelScope.launch {
            repository.getAllRewards()
                // catch tidak bisa disimpan setelah collect karena emang kek gitu XD,
                // untuk mengecheck exception jadi catch disimpan diawal
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderLaptops ->
                    _uiState.value = UiState.Success(orderLaptops)
                }
        }
    }
}