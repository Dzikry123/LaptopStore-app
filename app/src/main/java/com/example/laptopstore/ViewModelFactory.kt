package com.example.laptopstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laptopstore.ui.screen.home.HomeViewModel
import com.example.laptopstore.data.LaptopRepository
import com.example.laptopstore.ui.screen.cart.CartViewModel
import com.example.laptopstore.ui.screen.detail.DetailLaptopViewModel

class ViewModelFactory(private val repository: LaptopRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailLaptopViewModel::class.java)) {
            return DetailLaptopViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}