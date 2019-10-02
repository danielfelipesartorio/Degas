package com.sartorio.degas.ui

import com.sartorio.degas.model.Client

interface ClientRepository {
    fun getClientsList(): MutableList<Client>
    fun getClientByName(name: String): Client
    fun addNewClient(client: Client)
}