package com.sartorio.degas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey var uName: String,
    @ColumnInfo(name = "name") var name: ClientName,
    @ColumnInfo(name = "documents") var documents: ClientDocuments,
    @ColumnInfo(name = "clientAddress") var clientAddress: ClientAddress,
    @ColumnInfo(name = "contact") var contact: ClientContact
) {

    companion object {
        fun getNewClient(): Client {
            return Client(
                "",
                ClientName("", ""),
                ClientDocuments("", ""),
                ClientAddress("", "", "", "", ""),
                ClientContact("", "", "", "")
            )
        }
    }
}
