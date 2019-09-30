package com.sartorio.degas.ui.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.ProductRepository

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private lateinit var listOfOrders: MutableList<ProductOrder>

    fun plusOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.plusOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun lessOne(color: Int, size: String) {
        listOfOrders.find { it.productColor == color }?.lessOne(size)
        productOrders.postValue(listOfOrders)
    }

    fun initViewModel(code: String){
        product = productRepository.getProductByCode(code)
        listOfOrders = productRepository.getProductOrdersByProductCode(code)
        getProductOrders()
    }

    fun getProductOrders(){

        for (color in product.colors) {
            if (listOfOrders.find { it.productColor == color } == null){
                listOfOrders.add(ProductOrder(
                    product, color,
                    mutableMapOf()
                ))
            }
        }
        productOrders.postValue(listOfOrders)
    }

    fun getProduct():Product{
        return product
    }

    fun saveAlterations() {
        productRepository.updateOrderList(product,listOfOrders)
    }

    private lateinit var product:Product
    val productOrders = MutableLiveData<MutableList<ProductOrder>>()
}