package com.sartorio.degas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sartorio.degas.model.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM `order`")
    fun getAll(): List<Order>

    @Query("SELECT * FROM `order` WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Order>

    @Query("SELECT * FROM `order` WHERE id LIKE :code LIMIT 1")
    fun findByName(code: Int): Order

    @Insert
    fun insert(users: Order)

    @Delete
    fun delete(user: Order)
}
