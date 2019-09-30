package com.sartorio.degas.ui

import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder

interface ProductRepository {
    fun getProductList(): MutableList<Product>
    fun getProductByCode(code: String): Product
    fun getAllOrders():MutableList<ProductOrder>
    fun getProductOrdersByProductCode(code:String): MutableList<ProductOrder>
    fun updateOrderList(product: Product, listOfOrders: MutableList<ProductOrder>)
}