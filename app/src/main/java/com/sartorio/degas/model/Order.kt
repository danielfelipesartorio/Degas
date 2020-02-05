package com.sartorio.degas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Order(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "client") val client: Client,
    @ColumnInfo(name = "orderDate") val orderDate: Date,
    @ColumnInfo(name = "productList") var productList: MutableList<ProductOrder> = mutableListOf(),
    @ColumnInfo(name = "observations") var observations: String = "",
    @ColumnInfo(name = "paymentCondition") var paymentCondition : String = "",
    @ColumnInfo(name = "deliveryDate") var deliveryDate: Date = Date()
)
