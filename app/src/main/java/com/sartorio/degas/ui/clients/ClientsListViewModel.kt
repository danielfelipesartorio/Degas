package com.sartorio.degas.ui.clients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Client
import com.sartorio.degas.repository.ClientRepository

class ClientsListViewModel constructor(val clientesRepository: ClientRepository) :ViewModel() {
    val clientsList = MutableLiveData<MutableList<Client>>()

    fun initViewModel(){
        clientsList.postValue(clientesRepository.getClientsList())
    }

}
