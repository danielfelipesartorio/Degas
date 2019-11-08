package com.sartorio.degas.repository

import com.sartorio.degas.model.Product

interface ProductRepository {
    fun getProductList(): MutableList<Product>
    fun getProductByCode(code: String): Product
    fun updateProductList(productList : MutableList<Product>)
}