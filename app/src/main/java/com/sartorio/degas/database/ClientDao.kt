package com.sartorio.degas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sartorio.degas.model.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM client")
    fun getAll(): List<Client>

    @Query("SELECT * FROM client WHERE uName IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Client>

    @Query("SELECT * FROM client WHERE uName LIKE :name LIMIT 1")
    fun findByName(name: String): Client?

    @Insert
    fun insert(users: Client)

    @Delete
    fun delete(user: Client)
}
