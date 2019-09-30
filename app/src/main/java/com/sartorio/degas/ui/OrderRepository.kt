package com.sartorio.degas.ui

import com.sartorio.degas.model.Order
import java.util.*

interface OrderRepository {
    fun getOrdersList(): MutableList<Order>
    fun addNewOrder(clientName: String)
    fun deleteOrder(order: Order)
    fun getOrderByClient(companyName: String, date: Date): Order
}
