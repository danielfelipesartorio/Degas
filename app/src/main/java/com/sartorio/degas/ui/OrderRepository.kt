package com.sartorio.degas.ui

import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder

interface OrderRepository {
    fun getOrdersList(): MutableList<Order>
    fun addNewOrder(clientName: String)
    fun deleteOrder(order: Order)
    fun getOrderByClient(orderId: Int): Order
    fun getOrderDetails(orderId: Int): MutableList<ProductOrder>
    fun getProductOrdersByProductCode(
        code: String,
        orderId: Int
    ): MutableList<ProductOrder>

    fun removeOrder(productOrder: ProductOrder)

    fun updateOrderList(product: Product, listOfOrders: MutableList<ProductOrder>, orderId: Int)
}
