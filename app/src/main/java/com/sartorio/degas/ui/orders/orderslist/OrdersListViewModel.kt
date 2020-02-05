package com.sartorio.degas.ui.orders.orderslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.repository.ClientRepository
import com.sartorio.degas.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OrdersListViewModel(
    private val ordersRepository: OrderRepository,
    private val clientRepository: ClientRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {
    val ordersList = MutableLiveData<List<Order>>()
    private lateinit var clientNameList: List<String>

    fun getClientNameList(): List<String> {
        return if (::clientNameList.isInitialized) {
            clientNameList
        } else {
            emptyList()
        }
    }

    fun initViewModel() {
        getOrdersList()
        coroutineScope.launch {
            clientNameList =
                clientRepository.getClientsList().map { it.name.companyName }.toMutableList()
        }
    }

    private fun getOrdersList() {
        coroutineScope.launch {
            ordersList.postValue(ordersRepository.getOrdersList())
        }
    }

    fun addNewOrder(clientName: String) {
        coroutineScope.launch {
            ordersRepository.addNewOrder(clientName)
            getOrdersList()
        }
    }

    fun deleteOrder(order: Order) {
        coroutineScope.launch {
            ordersRepository.deleteOrder(order)
            getOrdersList()
        }
    }
}