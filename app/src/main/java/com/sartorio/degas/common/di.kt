package com.sartorio.degas.common

import com.sartorio.degas.ui.*
import com.sartorio.degas.ui.orderdetails.OrderDetailsViewModel
import com.sartorio.degas.ui.orderslist.OrdersListViewModel
import com.sartorio.degas.ui.productdetails.ProductViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { OrdersListViewModel(get(), get()) }
    viewModel { ProductViewModel(get()) }
    viewModel { OrderDetailsViewModel(get(), get()) }
    single { ClientRepositoryImpl() as ClientRepository }
    single { OrderRepositoryImpl() as OrderRepository }
    single { ProductRepositoryImpl() as ProductRepository }
}