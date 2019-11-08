package com.sartorio.degas.ui.orders.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.repository.OrderRepository
import com.sartorio.degas.repository.ProductRepository

class OrderDetailsViewModel(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val productOrderList = MutableLiveData<MutableList<ProductOrder>>()

    private lateinit var order: Order

    fun initViewModel(orderId: Int) {
        this.order = orderRepository.getOrderById(orderId)
        productOrderList.postValue(order.productList)
    }

    fun removeOrder(productOrder: ProductOrder) {
        orderRepository.removeOrder(productOrder)
        productOrderList.postValue(orderRepository.getOrderById(productOrder.orderId).productList)
    }

    fun getProductList(): List<Product> {
        return productRepository.getProductList()
    }

    fun getOrderByClient(orderId: Int): Order {
        return orderRepository.getOrderById(orderId)
    }
}