package com.sartorio.degas.ui

import com.sartorio.degas.model.Product

interface ProductRepository {
    fun getProductList(): MutableList<Product>
    fun getProductByCode(code: String): Product
}