package com.sartorio.degas.model

class ProductOrder(
    val product: Product,
    val productColor: Int,
    var quantity: MutableMap<String, Int>,
    val orderId: Int
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