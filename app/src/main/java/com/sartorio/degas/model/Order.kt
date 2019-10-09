package com.sartorio.degas.model

import java.util.*

class Order(
    val id: Int,
    val client: Client,
    val orderDate: Date,
    var productList: MutableList<ProductOrder> = mutableListOf(),
    var observations: String = "",
    var paymentCondition : String = "",
    var deliveryDate: Date = Date()
)
