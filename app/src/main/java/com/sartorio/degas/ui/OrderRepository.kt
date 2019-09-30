package com.sartorio.degas.ui

import com.sartorio.degas.model.Order

interface OrderRepository {
    fun getOrdersList(): MutableList<Order>
    fun addNewOrder(clientName: String)
    fun deleteOrder(order: Order)
}
