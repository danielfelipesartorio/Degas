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

    private var fakeOrdersList: MutableList<ProductOrder> =
        mutableListOf(
            ProductOrder(
                getProductByCode("01.01.0001"),
                getProductByCode("01.01.0001").colors[0],
                mutableMapOf("P" to 10, "M" to 2),
                "Cliente A",
                Date(1546300800000)
            ),
            ProductOrder(
                getProductByCode("01.01.0001"),
                getProductByCode("01.01.0001").colors[1],
                mutableMapOf("P" to 1, "M" to 2),
                "Cliente B",
                Date(1546300800000)
            ),
            ProductOrder(
                getProductByCode("01.01.0002"),
                getProductByCode("01.01.0002").colors[0],
                mutableMapOf("P" to 3, "G" to 2),
                "Cliente C",
                Date(1546300800000)
            )
        )


    override fun getProductByCode(code: String): Product {
        return fakeProductList.find { it.code == code } ?: throw Exception()
    }

    override fun getProductList(): MutableList<Product> {
        return fakeProductList
    }

    override fun getProductOrdersByProductCode(
        code: String,
        companyName: String,
        orderDate: Date
    ): MutableList<ProductOrder> {
        return fakeOrdersList.filter { it.product.code == code && it.clientName == companyName && it.orderDate == orderDate }
            .toMutableList()
    }

    override fun updateOrderList(
        product: Product,
        listOfOrders: MutableList<ProductOrder>,
        companyName: String,
        orderDate: Date
    ) {
        fakeOrdersList.removeAll { it.product.code == product.code && it.clientName == companyName && it.orderDate == orderDate }
        fakeOrdersList.addAll(listOfOrders.filter { it.quantity.values.sum() != 0 })
    }


    override fun getOrderDetails(companyName: String, date: Date): MutableList<ProductOrder> {
        return fakeOrdersList.filter { it.clientName == companyName && it.orderDate == date }.toMutableList()
    }

    override fun removeOrder(productOrder: ProductOrder) {
        fakeOrdersList.remove(productOrder)
    }
}