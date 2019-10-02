package com.sartorio.degas.ui.orderslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.ui.ClientRepository
import com.sartorio.degas.ui.OrderRepository

class OrdersListViewModel(
    private val ordersRepository: OrderRepository,
    private val clientRepository: ClientRepository
) : ViewModel() {
    val ordersList = MutableLiveData<MutableList<Order>>()

    fun getClientNameList(): MutableList<String> {
        return clientRepository.getClientsList().map { it.name.companyName }.toMutableList()
    }

    fun initViewModel() {
        getOrdersList()
    }

    fun getOrdersList() {
        ordersList.postValue(ordersRepository.getOrdersList())
    }

    fun addNewOrder(clientName: String) {
        ordersRepository.addNewOrder(clientName)
        getOrdersList()
    }

    fun deleteOrder(order: Order) {
        ordersRepository.deleteOrder(order)
        getOrdersList()
    }
}