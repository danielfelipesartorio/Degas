package com.sartorio.degas.repository

import com.sartorio.degas.database.OrderDao
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class OrderRepositoryImpl(
    private val coroutineScope: CoroutineScope,
    private val clientRepository: ClientRepository,
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun getOrdersList(): List<Order> {
        return orderDao.getAll()
    }

    override suspend fun addNewOrder(clientName: String) {
        orderDao.insert(
            Order(
                ((getOrdersList().maxByOrNull { it.id }?.id ?: 0) + 1),
                clientRepository.getClientByName(clientName),
                Date()
            )
        )
    }

    override suspend fun deleteOrder(order: Order) {
        orderDao.delete(order)
    }

    override suspend fun getOrderById(orderId: Int): Order {
        return orderDao.findByName(orderId)
    }

    override suspend fun updateOrderList(
        product: Product,
        listOfOrders: MutableList<ProductOrder>,
        orderId: Int
    ) {
        val temp = getOrderById(orderId)
        orderDao.delete(temp)
        val tempProductList = temp.productList.toMutableList()
        tempProductList.removeAll { it.product.code == product.code }
        tempProductList.addAll(listOfOrders.filter { it.quantity.values.sum() != 0 })
        temp.productList = tempProductList
        orderDao.insert(temp)
    }

    override suspend fun removeOrder(productOrder: ProductOrder) {
        coroutineScope.launch {
            getOrderById(productOrder.orderId).productList.remove(productOrder)
        }
    }

    override suspend fun deleteAll(): Boolean {
        getOrdersList().forEach {
            deleteOrder(it)
        }
        return true
    }
}