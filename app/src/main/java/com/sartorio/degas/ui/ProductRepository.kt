package com.sartorio.degas.ui

import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import java.util.*

interface ProductRepository {
    fun getProductList(): MutableList<Product>
    fun getProductByCode(code: String): Product
}