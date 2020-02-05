package com.sartorio.degas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sartorio.degas.model.Product

@Dao
interface  ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE code IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Product>

    @Query("SELECT * FROM product WHERE code LIKE :code LIMIT 1")
    fun findByName(code: String): Product

    @Insert
    fun insert(users: Product)

    @Delete
    fun delete(user: Product)
}
