package com.sartorio.degas.ui.clients.newclient

import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Client
import com.sartorio.degas.repository.ClientRepository

class NewClientViewModel(val clientRepository: ClientRepository) : ViewModel() {

    lateinit var client: Client

    fun saveNewClient(client: Client) {
        clientRepository.removeClient(clientRepository.getClientByName(client.name.companyName))
        clientRepository.addNewClient(client)
    }

    fun initViewModel(clientName: String) {
        client = clientRepository.getClientByName(clientName)
    }

    fun getCurrentClient(): Client{
        return client
    }
}