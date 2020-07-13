package com.sartorio.degas.repository

import com.sartorio.degas.model.Client

interface ClientRepository {
    suspend fun getClientsList(): List<Client>
    suspend fun getClientByName(name: String): Client
    suspend fun addNewClient(client: Client)
    suspend fun removeClient(client: Client)
    suspend fun updateClientList(newProductsList: MutableList<Client>)
}