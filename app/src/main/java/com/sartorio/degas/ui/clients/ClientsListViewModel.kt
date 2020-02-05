package com.sartorio.degas.ui.clients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Client
import com.sartorio.degas.repository.ClientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ClientsListViewModel constructor(
    private val clientesRepository: ClientRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {
    val clientsList = MutableLiveData<List<Client>>()

    fun initViewModel() {
        coroutineScope.launch {
            clientsList.postValue(clientesRepository.getClientsList())
        }
    }

}
