package com.example.laptopstore.di

import com.example.laptopstore.data.LaptopRepository


object Injection {
    fun provideRepository(): LaptopRepository {
        return LaptopRepository.getInstance()
    }
}