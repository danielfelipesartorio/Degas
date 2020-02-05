package com.sartorio.degas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val code: String,
    @ColumnInfo(name = "colors") val colors: List<Int>,
    @ColumnInfo(name = "sizes") val sizes: MutableMap<String,Int>,
    @ColumnInfo(name = "cost") val cost: Double
)

