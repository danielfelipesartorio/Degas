package com.sartorio.degas.repository

import com.sartorio.degas.database.ClientDao
import com.sartorio.degas.model.Client

class ClientRepositoryImpl(
    private val clientDao: ClientDao
) : ClientRepository {

    override suspend fun getClientsList(): List<Client> {
        return clientDao.getAll()
    }

    override suspend fun getClientByName(name: String): Client {
        return clientDao.findByName(name) ?: Client.getNewClient()
    }

    override suspend fun addNewClient(client: Client) {
        clientDao.insert(client)
    }

    override suspend fun removeClient(client: Client) {
        clientDao.delete(client)
    }

}