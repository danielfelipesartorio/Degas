package com.sartorio.degas.ui.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.OrderRepository
import com.sartorio.degas.ui.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private lateinit var listOfOrders: MutableList<ProductOrder>
    private lateinit var order: Order
    private lateinit var product: Product
    val productOrders = MutableLiveData<MutableList<ProductOrder>>()

    fun plusOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.plusOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun lessOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.lessOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun initViewModel(code: String, orderId: Int) {
        this.order = orderRepository.getOrderById(orderId)
        product = productRepository.getProductByCode(code)
        listOfOrders = mutableListOf()
        listOfOrders.addAll(order.productList.filter { it.product.code == code })
        getProductOrders()
    }

    fun getProductOrders() {
        for (color in product.colors) {
            if (listOfOrders.find { it.productColor == color } == null) {
                listOfOrders.add(
                    ProductOrder(
                        product, color,
                        mutableMapOf(),
                        order.id
                    )
                )
            }
        }
        productOrders.postValue(listOfOrders)
    }

    fun getProduct(): Product {
        return product
    }

    fun saveAlterations() {
        orderRepository.updateOrderList(product, listOfOrders, order.id)
    }


}