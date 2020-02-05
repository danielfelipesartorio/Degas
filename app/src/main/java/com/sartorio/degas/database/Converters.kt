package com.sartorio.degas.database

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


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
    fun mapToJson(value: Map<String,Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMap(value: String): Map<String,Int>? {
        return Gson().fromJson(value,  object : TypeToken<Map<String, String>>() {}.type)
    }
}