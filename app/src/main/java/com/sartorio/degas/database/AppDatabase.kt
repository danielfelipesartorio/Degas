package com.sartorio.degas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sartorio.degas.model.Client
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.Product

@Database(entities = arrayOf(Product::class, Order::class, Client::class), version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun ordersDao(): OrderDao
    abstract fun clientDao(): ClientDao
}