package com.sartorio.degas.ui.clients.newclient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Client
import com.sartorio.degas.repository.ClientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewClientViewModel(
    private val clientRepository: ClientRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    val client = MutableLiveData<Client>()

    fun saveNewClient(client: Client) {
        coroutineScope.launch {
            clientRepository.removeClient(clientRepository.getClientByName(client.name.companyName))
            clientRepository.addNewClient(client)
        }
    }

    fun initViewModel(clientName: String) {
        coroutineScope.launch {
            client.postValue( clientRepository.getClientByName(clientName))
        }
    }
}