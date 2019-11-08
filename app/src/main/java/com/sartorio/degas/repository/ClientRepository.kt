package com.sartorio.degas.repository

import com.sartorio.degas.model.Client

interface ClientRepository {
    fun getClientsList(): MutableList<Client>
    fun getClientByName(name: String): Client
    fun addNewClient(client: Client)
    fun removeClient(client: Client)
}