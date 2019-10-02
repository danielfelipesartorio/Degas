package com.sartorio.degas.ui

import com.sartorio.degas.model.Client
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import java.lang.Exception
import java.util.*

class OrderRepositoryImpl(productRepository: ProductRepository) : OrderRepository {

    var fakeOrdersList = mutableListOf(
        Order(
            1, Client("Cliente A"), Date(1546300800000), mutableListOf(
                ProductOrder(
                    productRepository.getProductByCode("01.01.0001"),
                    productRepository.getProductByCode("01.01.0001").colors[0],
                    mutableMapOf("P" to 10, "M" to 2),
                    1
                )
            )
        ),
        Order(
            2, Client("Cliente B"), Date(1546300800000), mutableListOf(
                ProductOrder(
                    productRepository.getProductByCode("01.01.0001"),
                    productRepository.getProductByCode("01.01.0001").colors[1],
                    mutableMapOf("P" to 1, "M" to 2),
                    2
                )
            )
        ),
        Order(
            3, Client("Cliente C"), Date(1546300800000), mutableListOf(
                ProductOrder(
                    productRepository.getProductByCode("01.01.0002"),
                    productRepository.getProductByCode("01.01.0002").colors[0],
                    mutableMapOf("P" to 3, "G" to 2),
                    3
                )
            )
        )
    )

    override fun getOrdersList(): MutableList<Order> {
        return fakeOrdersList
    }

    override fun addNewOrder(clientName: String) {
        fakeOrdersList.add(Order(4, Client(clientName), Date()))
    }

    override fun deleteOrder(order: Order) {
        fakeOrdersList.remove(order)
    }

    override fun getOrderByClient(orderId: Int): Order {
        return fakeOrdersList.find { it.id == orderId } ?: throw Exception()
    }

    override fun getProductOrdersByProductCode(
        code: String,
        orderId: Int
    ): MutableList<ProductOrder> {
        return (fakeOrdersList.find { it.id == orderId } ?: throw Exception()).productList.filter { it.product.code == code }
            .toMutableList()
    }

    override fun updateOrderList(
        product: Product,
        listOfOrders: MutableList<ProductOrder>,
        orderId: Int
    ) {
        (fakeOrdersList.find { it.id == orderId } ?: throw Exception()).productList.removeAll { it.product.code == product.code }
        (fakeOrdersList.find { it.id == orderId } ?: throw Exception()).productList.addAll(listOfOrders.filter { it.quantity.values.sum() != 0 })
    }


    override fun getOrderDetails(orderId: Int): MutableList<ProductOrder> {
        return (fakeOrdersList.find { it.id == orderId } ?: throw Exception()).productList
    }

    override fun removeOrder(productOrder: ProductOrder) {
        fakeOrdersList[productOrder.orderId].productList.remove(productOrder)
    }
}