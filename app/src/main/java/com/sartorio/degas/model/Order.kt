package com.sartorio.degas.model

class Order(
    val clientName : String,
    var productList : List<Product> = listOf()
)
