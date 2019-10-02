package com.sartorio.degas.model

import java.util.*

class Order(
    val id: Int,
    val client: Client,
    val date: Date,
    var productList: MutableList<ProductOrder> = mutableListOf()
)
