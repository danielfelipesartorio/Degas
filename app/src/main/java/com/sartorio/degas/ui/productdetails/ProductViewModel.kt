package com.sartorio.degas.ui.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.ProductRepository
import java.util.*

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private lateinit var listOfOrders: MutableList<ProductOrder>
    private lateinit var companyName: String
    private lateinit var orderDate: Date

    fun plusOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.plusOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun lessOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.lessOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun initViewModel(code: String, companyName: String, orderDate: Date) {
        this.companyName = companyName
        this.orderDate = orderDate
        product = productRepository.getProductByCode(code)
        listOfOrders = productRepository.getProductOrdersByProductCode(code,companyName,orderDate)
        getProductOrders()
    }

    fun getProductOrders() {
        for (color in product.colors) {
            if (listOfOrders.find { it.productColor == color } == null) {
                listOfOrders.add(
                    ProductOrder(
                        product, color,
                        mutableMapOf(),
                        companyName,
                        orderDate
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
        productRepository.updateOrderList(product, listOfOrders, companyName, orderDate)
    }

    private lateinit var product: Product
    val productOrders = MutableLiveData<MutableList<ProductOrder>>()
}