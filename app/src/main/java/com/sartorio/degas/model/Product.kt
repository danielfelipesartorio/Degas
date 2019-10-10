package com.sartorio.degas.model

class Product(
    val code: String,
    val colors: List<Int>,
    val sizes: MutableMap<String,Int>,
    val cost: Double
)

