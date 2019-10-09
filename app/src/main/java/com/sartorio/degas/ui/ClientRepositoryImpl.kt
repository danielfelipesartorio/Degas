package com.sartorio.degas.ui

import com.sartorio.degas.model.*

class ClientRepositoryImpl : ClientRepository {

    var fakeClientList = mutableListOf<Client>(
        Client(
            ClientName("Cliente A", "Fantasia A"),
            ClientDocuments("cpf", "inscrição estadual"),
            ClientAddress("Rua Ágata", "87083-320", "jardim Santa Helena", "Maringá", "PR"),
            ClientContact("Daniel", "email@email.com", "44-3046-3887", "44-99932-3498")
        ),
        Client(
            ClientName("Cliente B", "Fantasia B"),
            ClientDocuments("cpf", "inscrição estadual"),
            ClientAddress("", "", "", "", ""),
            ClientContact("", "", "", "")
        ),
        Client(
            ClientName("Cliente C", "Fantasia C"),
            ClientDocuments("cpf", "inscrição estadual"),
            ClientAddress("", "", "", "", ""),
            ClientContact("", "", "", "")
        )
    )


    override fun getClientsList(): MutableList<Client> {
        return fakeClientList
    }

    override fun getClientByName(name: String): Client {
        return fakeClientList.find { it.name.companyName == name } ?: throw Exception()
    }

    override fun addNewClient(client: Client) {
        fakeClientList.add(client)
    }

}