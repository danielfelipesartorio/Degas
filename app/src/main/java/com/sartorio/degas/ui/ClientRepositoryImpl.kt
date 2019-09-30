package com.sartorio.degas.ui

import com.sartorio.degas.model.Client
import java.lang.Exception

class ClientRepositoryImpl : ClientRepository {
    var fakeClientList = mutableListOf<Client>(
        Client("Cliente A"),
        Client("Cliente B"),
        Client("Cliente C"),
        Client("Cliente D"),
        Client("Cliente E"),
        Client("Cliente F"),
        Client("Cliente G"),
        Client("Cliente H"),
        Client("Cliente I")
    )


    override fun getClientsList(): MutableList<Client> {
        return fakeClientList
    }

    override fun getClientByName(name: String): Client {
        return fakeClientList.find { it.companyName == name } ?: throw Exception()
    }
}