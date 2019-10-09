package com.sartorio.degas.ui.newclient

import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Client
import com.sartorio.degas.ui.ClientRepository

class NewClientViewModel(val clientRepository: ClientRepository) : ViewModel() {
    fun saveNewClient(client: Client) {
        clientRepository.addNewClient(client)
    }
}