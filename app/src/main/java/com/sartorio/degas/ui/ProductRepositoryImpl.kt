package com.sartorio.degas.ui

import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import java.util.*

class ProductRepositoryImpl : ProductRepository {


    private var fakeProductList: MutableList<Product> = mutableListOf(
        Product("01.01.0001", listOf(100, 1), listOf("P", "M", "G"), 10.0),
        Product("01.01.0002", listOf(150, 200), listOf("P", "M", "G"), 10.0),
        Product("01.01.0003", listOf(100, 200), listOf("P", "M", "G"), 10.0),
        Product("01.01.0004", listOf(1, 15), listOf("P", "M", "G"), 10.0),
        Product("01.01.0005", listOf(1, 2), listOf("P", "M", "G"), 10.0),
        Product(
            "01.01.0006",
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            listOf("PP", "P", "M", "G", "GG", "XG", "XXG", "XXXG"),
            10.0
        )
    )

    override fun getProductByCode(code: String): Product {
        return fakeProductList.find { it.code == code } ?: throw Exception()
    }

    override fun getProductList(): MutableList<Product> {
        return fakeProductList
    }


}