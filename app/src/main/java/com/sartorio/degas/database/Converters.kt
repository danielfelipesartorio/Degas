package com.sartorio.degas.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sartorio.degas.model.*
import java.util.*


class Converters {

    //Converter for List<Int>
    @TypeConverter
    fun listToJson(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<Int>? =
        (Gson().fromJson(value, Array<Int>::class.java) as Array<Int>).toList()

    //Converter for Map<String,Int>
    @TypeConverter
    fun mapToJson(value: Map<String, Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToMap(value: String): Map<String, Int>? {
        return Gson().fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    }

    //Converter for List<ProductOrder>
    @TypeConverter
    fun listProductToJson(value: List<ProductOrder>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListProduct(value: String): List<ProductOrder>? {
        val objects = Gson().fromJson(value, Array<ProductOrder>::class.java) as Array<ProductOrder>
        val list = objects.toList()
        return list
    }

    //Date
    @TypeConverter
    fun dateToJson(value: Date?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToDate(value: String): Date? {
        return Gson().fromJson(value, Date::class.java) as Date
    }

    //Client
    @TypeConverter
    fun clientToJson(value: Client?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClient(value: String): Client? {
        return Gson().fromJson(value, Client::class.java) as Client
    }

    //ClientName
    @TypeConverter
    fun clientNameToJson(value: ClientName?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClientName(value: String): ClientName? {
        return Gson().fromJson(value, ClientName::class.java) as ClientName
    }

    //ClientDocuments
    @TypeConverter
    fun clientDocumentsToJson(value: ClientDocuments?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClientDocuments(value: String): ClientDocuments? {
        return Gson().fromJson(value, ClientDocuments::class.java) as ClientDocuments
    }

    //ClientAddress
    @TypeConverter
    fun clientAddressToJson(value: ClientAddress?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClientAddress(value: String): ClientAddress? {
        return Gson().fromJson(value, ClientAddress::class.java) as ClientAddress
    }

    //ClientContact
    @TypeConverter
    fun clientContactToJson(value: ClientContact?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToClientContact(value: String): ClientContact? {
        return Gson().fromJson(value, ClientContact::class.java) as ClientContact
    }
}