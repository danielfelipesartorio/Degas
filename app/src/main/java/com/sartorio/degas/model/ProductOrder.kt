package com.sartorio.degas.model

import java.util.*

class ProductOrder(
    val product: Product,
    val productColor: Int,
    var quantity: MutableMap<String, Int>,
    val clientName: String,
    val orderDate: Date
) {

    fun plusOne(size: String) {
        quantity[size] = quantity[size]?.plus(1) ?: 1

    }

    fun lessOne(size: String) {
        var temp = quantity[size]?.minus(1) ?: 0
        if (temp < 0) temp = 0
        quantity[size] = temp
    }
}