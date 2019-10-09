package com.sartorio.degas.ui.exportorder

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.ui.OrderRepository
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExportOrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private lateinit var order: Order

    val clientName = ObservableField<String>()
    val orderDate = ObservableField<String>()
    val orderAmount = ObservableField<String>()
    val orderCost = ObservableField<String>()
    val deliveryDate = ObservableField<String>()
    val paymentOptions = ObservableField<String>()
    val orderObservations = ObservableField<String>()


    fun initViewModel(orderId: Int) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val numberFormat = NumberFormat.getCurrencyInstance()

        this.order = orderRepository.getOrderById(orderId)
        clientName.set("Cliente: " + order.client.name.companyName)
        orderDate.set("Data: " + dateFormat.format(order.orderDate))
        orderAmount.set("Quantidade: " + order.productList.sumBy { it.quantity.values.sum() }.toString())
        orderCost.set("Total: " + numberFormat.format(order.productList.sumByDouble { it.quantity.values.sum() * it.product.cost }))
        deliveryDate.set(dateFormat.format(order.deliveryDate))
        paymentOptions.set(order.paymentCondition)
        orderObservations.set(order.observations)
    }

    fun getOrderByClient(): Order {
        return order
    }
}