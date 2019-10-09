package com.sartorio.degas.common

import com.google.gson.Gson
import com.sartorio.degas.ui.*
import com.sartorio.degas.ui.exportorder.ExportOrderViewModel
import com.sartorio.degas.ui.newclient.NewClientViewModel
import com.sartorio.degas.ui.orderdetails.OrderDetailsViewModel
import com.sartorio.degas.ui.orderslist.OrdersListViewModel
import com.sartorio.degas.ui.productdetails.ProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    viewModel { OrdersListViewModel(get(), get()) }
    viewModel { ProductViewModel(get(), get()) }
    viewModel { OrderDetailsViewModel(get(), get()) }
    viewModel { NewClientViewModel(get()) }
    viewModel { ExportOrderViewModel(get()) }
    single { ClientRepositoryImpl() as ClientRepository }
    single { OrderRepositoryImpl(get(), get()) as OrderRepository }
    single { ProductRepositoryImpl() as ProductRepository }

}

val netWorkModule = module {
    single {
        val gsonConverterFactory: GsonConverterFactory = get()
        val okHttpClient: OkHttpClient = get()
        Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build() as Retrofit
    }
    single {
        val gson: Gson = get()
        GsonConverterFactory.create(gson) as GsonConverterFactory
    }
    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor
    }
    single { Gson() }
    single {
        val httpLoggingInterceptor: HttpLoggingInterceptor = get()
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build() as OkHttpClient
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(CepService::class.java) as CepService
    }
}