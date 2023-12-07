package com.example.laptopstore.data

import com.example.laptopstore.model.FakeLaptopDataSource
import com.example.laptopstore.model.OrderLaptop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class LaptopRepository {

    private val orderLaptops = mutableListOf<OrderLaptop>()

    init {
        if (orderLaptops.isEmpty()) {
            FakeLaptopDataSource.dummyRewards.forEach {
                orderLaptops.add(OrderLaptop(it, 0))
            }
        }
    }

    fun getAllRewards(): Flow<List<OrderLaptop>> {
        return flowOf(orderLaptops)
    }

    fun getOrderLaptopById(laptopId: Long): OrderLaptop {
        return orderLaptops.first {
            it.laptop.id == laptopId
        }
    }

    fun updateOrderLaptop(laptopId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderLaptops.indexOfFirst { it.laptop.id == laptopId }
        val result = if (index >= 0) {
            val orderLaptop = orderLaptops[index]
            orderLaptops[index] =
                orderLaptop.copy(laptop = orderLaptop.laptop, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderLaptops(): Flow<List<OrderLaptop>> {
        return getAllRewards()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: LaptopRepository? = null

        fun getInstance(): LaptopRepository =
            instance ?: synchronized(this) {
                LaptopRepository().apply {
                    instance = this
                }
            }
    }
}