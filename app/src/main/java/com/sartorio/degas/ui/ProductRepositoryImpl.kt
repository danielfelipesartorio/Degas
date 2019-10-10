package com.sartorio.degas.ui

import com.sartorio.degas.model.Product

class ProductRepositoryImpl : ProductRepository {


    private var fakeProductList: MutableList<Product> = mutableListOf(
        Product("01.01.0001", listOf(100, 1), mutableMapOf("P" to 20, "M" to 20, "G" to 20), 10.0),
        Product("01.01.0002", listOf(150, 200), mutableMapOf("P" to 20, "M" to 20, "G" to 20), 10.0),
        Product("01.01.0003", listOf(100, 200), mutableMapOf("P" to 20, "M" to 20, "G" to 20), 10.0),
        Product("01.01.0004", listOf(1, 15), mutableMapOf("P" to 20, "M" to 20, "G" to 20), 10.0),
        Product(
            "01.01.0005",
            listOf(1, 2, 3, 4, 5, 6, 7, 8),
            mutableMapOf("P" to 20, "M" to 20, "G" to 20, "GG" to 20),
            10.0
        ),
        Product(
            "01.01.0006",
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            mutableMapOf(
                "PP" to 20,
                "P" to 20,
                "M" to 20,
                "G" to 20,
                "GG" to 20,
                "XG" to 20,
                "XXG" to 20,
                "XXXG" to 20
            ),
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