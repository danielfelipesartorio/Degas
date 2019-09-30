package com.sartorio.degas.ui.orderdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.ProductRepository

class OrderViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val orderList = MutableLiveData<MutableList<ProductOrder>>()

    fun initViewModel(){
        orderList.postValue(productRepository.getAllOrders())
    }

    fun removeOrder(productOrder: ProductOrder){
        productRepository.getAllOrders().remove(productOrder)
        orderList.postValue(productRepository.getAllOrders())
    }

    fun getProductList(): List<Product> {
        return productRepository.getProductList()
    }
}