package com.sartorio.degas.ui

import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import java.util.*

interface ProductRepository {
    fun getProductList(): MutableList<Product>
    fun getProductByCode(code: String): Product
    fun getOrderDetails(companyName: String, date: Date): MutableList<ProductOrder>
    fun getProductOrdersByProductCode(
        code: String,
        companyName: String,
        orderDate: Date
    ): MutableList<ProductOrder>
    fun removeOrder(productOrder: ProductOrder)

    fun updateOrderList(product: Product, listOfOrders: MutableList<ProductOrder>, companyName: String, orderDate: Date)
}