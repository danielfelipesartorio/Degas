package com.sartorio.degas.ui.collections

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sartorio.degas.repository.OrderRepository
import com.sartorio.degas.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CollectionsListViewModel constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    val deleteSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun deleteAll() {
        coroutineScope.launch {
            deleteSuccess.postValue(
                orderRepository.deleteAll()  && productRepository.deleteAll()
            )
        }
    }
}