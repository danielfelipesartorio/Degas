package com.sartorio.degas.ui.collections.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.repository.OrderRepository
import com.sartorio.degas.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private lateinit var listOfOrders: MutableList<ProductOrder>
    private lateinit var order: Order
    private lateinit var product: Product
    val productOrders = MutableLiveData<MutableList<ProductOrder>>()
    val updateSuccess = MutableLiveData<Unit>()

    fun plusOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.plusOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun lessOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.lessOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun initViewModel(code: String, orderId: Int) {
        coroutineScope.launch {
            order = orderRepository.getOrderById(orderId)
            product = productRepository.getProductByCode(code)
            listOfOrders = mutableListOf()
            listOfOrders.addAll(order.productList.filter { it.product.code == code })
            getProductOrders()
        }
    }

    private fun getProductOrders() {
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
        coroutineScope.launch {
            orderRepository.updateOrderList(product, listOfOrders, order.id)
            updateSuccess.postValue(Unit)
        }
    }

}