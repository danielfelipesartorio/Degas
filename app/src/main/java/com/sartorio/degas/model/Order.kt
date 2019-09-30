package com.sartorio.degas.model

import java.util.*

class Order(
    val clientName: String,
    val date: Date,
    var productList: List<Product> = listOf()
)
