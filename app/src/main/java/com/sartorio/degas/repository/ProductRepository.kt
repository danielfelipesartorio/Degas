package com.sartorio.degas.repository

import com.sartorio.degas.model.Product

interface ProductRepository {
    suspend fun getProductList(): List<Product>
    suspend fun getProductByCode(code: String): Product
    suspend fun updateProductList(productList : MutableList<Product>)
}