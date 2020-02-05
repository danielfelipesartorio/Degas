package com.sartorio.degas.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sartorio.degas.model.Client
import com.sartorio.degas.model.ProductOrder
import java.util.*


class Converters {

    @TypeConverter
    fun listToJson(value: List<Int>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Int>? {

        val objects = Gson().fromJson(value, Array<Int>::class.java) as Array<Int>
        val list = objects.toList()
        return list
    }

    @TypeConverter
    fun mapToJson(value: Map<String, Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMap(value: String): Map<String, Int>? {
        return Gson().fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    }

    @TypeConverter
    fun listProductToJson(value: List<ProductOrder>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToListProduct(value: String): List<ProductOrder>? {
        val objects = Gson().fromJson(value, Array<ProductOrder>::class.java) as Array<ProductOrder>
        val list = objects.toList()
        return list
    }

    @TypeConverter
    fun dateToJson(value: Date?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToDate(value: String): Date? {
        return Gson().fromJson(value, Date::class.java) as Date
    }

    @TypeConverter
    fun clientToJson(value: Client?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClient(value: String): Client? {
        return Gson().fromJson(value, Client::class.java) as Client
    }
}