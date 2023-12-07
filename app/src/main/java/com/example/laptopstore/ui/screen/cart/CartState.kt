package com.example.laptopstore.ui.screen.cart

import com.example.laptopstore.model.OrderLaptop

data class CartState(
    val orderLaptop: List<OrderLaptop>,
    val totalRequiredPrice: Int
)