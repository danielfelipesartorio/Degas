package com.sartorio.degas.ui

import com.sartorio.degas.model.Order
import java.util.*

class OrderRepositoryImpl : OrderRepository {

    var fakeOrdersList = mutableListOf(
        Order("Cliente A", Date(1546300800000)),
        Order("Cliente B", Date(1546300800000)),
        Order("Cliente C", Date(1546300800000))
    )

    override fun getOrdersList(): MutableList<Order> {
        return fakeOrdersList
    }

    override fun addNewOrder(clientName: String) {
        fakeOrdersList.add(Order(clientName, Date()))
    }

    override fun deleteOrder(order: Order) {
        fakeOrdersList.remove(order)
    }

    override fun getOrderByClient(companyName: String, date: Date): Order {
        return fakeOrdersList.find { it.clientName == companyName && it.date == date }
            ?: throw Exception()
    }
}