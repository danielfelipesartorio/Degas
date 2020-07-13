package com.sartorio.degas.repository

import com.sartorio.degas.database.ProductDao
import com.sartorio.degas.model.Product

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProductByCode(code: String): Product {
        return productDao.findByName(code)
    }

    override suspend fun getProductList(): MutableList<Product> {
        return productDao.getAll().toMutableList()
    }

    override suspend fun updateProductList(productList: MutableList<Product>) {
        productList.forEach {
            productDao.delete(it)
            productDao.insert(it)
        }
    }

    override suspend fun deleteAll() : Boolean {
        productDao.getAll().forEach {
            productDao.delete(it)
        }
        return true
    }
}