package com.sartorio.degas.ui

import com.sartorio.degas.model.Order

class OrderRepositoryImpl : OrderRepository {

    var fakeOrdersList = mutableListOf(
        Order("Cliente A"),
        Order("Cliente B"),
        Order("Cliente C")
    )

    override fun getOrdersList(): MutableList<Order> {
        return fakeOrdersList
    }

    override fun addNewOrder(clientName: String) {
        fakeOrdersList.add(Order(clientName))
    }

    override fun deleteOrder(order: Order) {
        fakeOrdersList.remove(order)
    }

}