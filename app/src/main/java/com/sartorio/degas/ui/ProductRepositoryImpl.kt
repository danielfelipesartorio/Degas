package com.sartorio.degas.ui

import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder

class ProductRepositoryImpl : ProductRepository {
    private var fakeProductList: MutableList<Product> = mutableListOf(
        Product("01.01.0001", listOf(100, 1), listOf("P", "M", "G")),
        Product("01.01.0002", listOf(150, 200), listOf("P", "M", "G")),
        Product("01.01.0003", listOf(100, 200), listOf("P", "M", "G")),
        Product("01.01.0004", listOf(1, 15), listOf("P", "M", "G")),
        Product("01.01.0005", listOf(1, 2), listOf("P", "M", "G")),
        Product(
            "01.01.0006",
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            listOf("PP", "P", "M", "G", "GG", "XG", "XXG", "XXXG")
        )
    )

    private var fakeOrdersList: MutableList<ProductOrder> =
        mutableListOf(
            ProductOrder(
                Product("01.01.0001"),
                1,
                mutableMapOf("P" to 10, "M" to 2)
            ),
            ProductOrder(
                Product("01.01.0001"),
                2,
                mutableMapOf("P" to 1, "M" to 2)
            ),
            ProductOrder(
                Product("01.01.0002"),
                1,
                mutableMapOf("P" to 100, "G" to 2)
            )
        )


    override fun getProductByCode(code: String): Product {
        return fakeProductList.find { it.code == code } ?: throw Exception()
    }

    override fun getProductList(): MutableList<Product> {
        return fakeProductList
    }

    override fun getProductOrdersByProductCode(code: String): MutableList<ProductOrder> {
        return fakeOrdersList.filter { it.product.code == code }.toMutableList()
    }

    override fun updateOrderList(product: Product, listOfOrders: MutableList<ProductOrder>) {
        fakeOrdersList.removeAll { it.product.code == product.code }
        fakeOrdersList.addAll(listOfOrders.filter { it.quantity.values.sum() != 0 })
    }

    override fun getAllOrders(): MutableList<ProductOrder> {
        return fakeOrdersList
    }

}