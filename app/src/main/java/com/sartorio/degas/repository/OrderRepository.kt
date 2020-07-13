package com.sartorio.degas.repository

import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder

interface OrderRepository {
    suspend fun getOrdersList(): List<Order>
    suspend fun addNewOrder(clientName: String)
    suspend fun deleteOrder(order: Order)
    suspend fun getOrderById(orderId: Int): Order
    suspend fun removeOrder(productOrder: ProductOrder)
    suspend fun updateOrderList(product: Product, listOfOrders: MutableList<ProductOrder>, orderId: Int)
    suspend fun deleteAll(): Boolean
}
