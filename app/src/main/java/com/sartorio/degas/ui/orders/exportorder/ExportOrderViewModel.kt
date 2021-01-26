package com.sartorio.degas.ui.orders.exportorder

import android.os.Build
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Order
import com.sartorio.degas.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExportOrderViewModel(
    private val orderRepository: OrderRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    lateinit var order: Order

    val clientName = ObservableField<String>()
    val orderDate = ObservableField<String>()
    val orderAmount = ObservableField<String>()
    val orderCost = ObservableField<String>()
    val deliveryDate = ObservableField<String>()
    val paymentOptions = ObservableField<String>()
    val orderObservations = ObservableField<String>()
    val focusable = ObservableField<Boolean>(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
    val dateFormat by lazy {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
    val numberFormat by lazy {
        NumberFormat.getCurrencyInstance()
    }

    fun initViewModel(orderId: Int) {

        coroutineScope.launch {
            order = orderRepository.getOrderById(orderId)
            clientName.set("Cliente: " + order.client.name.companyName)
            orderDate.set("Data: " + dateFormat.format(order.orderDate))
            orderAmount.set("Quantidade: " + order.productList.sumBy { it.quantity.values.sum() }.toString())
            orderCost.set("Total: " + numberFormat.format(order.productList.sumByDouble { it.quantity.values.sum() * it.product.cost }))
            deliveryDate.set(dateFormat.format(order.deliveryDate))
            paymentOptions.set(order.paymentCondition)
            orderObservations.set(order.observations)
        }
    }

    fun setDeliveryDate(day: Int, month: Int, year: Int) {
        deliveryDate.set(String.format("%02d/%02d/%04d", day, month+1, year))
    }
}