package com.sartorio.degas.ui.orders.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.repository.OrderRepository
import com.sartorio.degas.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    val productOrderList = MutableLiveData<MutableList<ProductOrder>>()
    val orderMutable = MutableLiveData<Order>()
    private lateinit var productList: List<Product>

    private lateinit var order: Order

    fun initViewModel(orderId: Int) {
        coroutineScope.launch {
            order = orderRepository.getOrderById(orderId)
            productOrderList.postValue(order.productList)
            productList = productRepository.getProductList()
            orderMutable.postValue(order)
        }
    }

    fun removeOrder(productOrder: ProductOrder) {
        coroutineScope.launch {
            orderRepository.removeOrder(productOrder)
            productOrderList.postValue(orderRepository.getOrderById(productOrder.orderId).productList)
        }
    }

    fun getProductList(): List<Product> {
        return if (::productList.isInitialized) {
            productList
        } else {
            emptyList()
        }
    }
}