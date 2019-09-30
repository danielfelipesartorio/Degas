package com.sartorio.degas.ui.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.OrderRepository
import com.sartorio.degas.ui.ProductRepository
import java.util.*

class OrderDetailsViewModel(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val productOrderList = MutableLiveData<MutableList<ProductOrder>>()
    private lateinit var companyName: String
    private lateinit var orderDate: Date

    fun initViewModel(companyName: String, date: Date) {
        this.companyName = companyName
        this.orderDate = date
        val order = productRepository.getOrderDetails(companyName, date)
        productOrderList.postValue(order)
    }

    fun removeOrder(productOrder: ProductOrder) {
        productRepository.removeOrder(productOrder)
        productOrderList.postValue(productRepository.getOrderDetails(companyName, orderDate))
    }

    fun getProductList(): List<Product> {
        return productRepository.getProductList()
    }

    fun getOrderByClient(companyName: String, date: Date): Order {
        return orderRepository.getOrderByClient(companyName, date)
    }
}