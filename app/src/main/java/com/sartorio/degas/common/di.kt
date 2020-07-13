package com.sartorio.degas.common

import androidx.room.Room
import com.google.gson.Gson
import com.sartorio.degas.database.AppDatabase
import com.sartorio.degas.repository.*
import com.sartorio.degas.services.CepService
import com.sartorio.degas.ui.clients.ClientsListViewModel
import com.sartorio.degas.ui.clients.importclient.ImportClientViewModel
import com.sartorio.degas.ui.clients.newclient.NewClientViewModel
import com.sartorio.degas.ui.collections.CollectionsListViewModel
import com.sartorio.degas.ui.collections.addproducts.AddProductViewModel
import com.sartorio.degas.ui.collections.productdetails.ProductViewModel
import com.sartorio.degas.ui.orders.exportorder.ExportOrderViewModel
import com.sartorio.degas.ui.orders.orderdetails.OrderDetailsViewModel
import com.sartorio.degas.ui.orders.orderslist.OrdersListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    factory { CoroutineScope(Dispatchers.Default) }

    viewModel { OrdersListViewModel(get(), get(), get()) }
    viewModel { ProductViewModel(get(), get(), get()) }
    viewModel { OrderDetailsViewModel(get(), get(), get()) }
    viewModel { NewClientViewModel(get(),get()) }
    viewModel { ExportOrderViewModel(get(), get()) }
    viewModel { AddProductViewModel(get(), get()) }
    viewModel { ClientsListViewModel(get(),get()) }
    viewModel { CollectionsListViewModel(get(),get(),get()) }
    viewModel { ImportClientViewModel(get(),get()) }
    single { ClientRepositoryImpl(get()) as ClientRepository }
    single {
        OrderRepositoryImpl(
            get(),
            get(),
            get()
        ) as OrderRepository
    }
    single {
        ProductRepositoryImpl(
            get()
        ) as ProductRepository
    }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "degas-database"
        )
            .build()
    }

    single { get<AppDatabase>().productDao() }

    single { get<AppDatabase>().ordersDao() }

    single { get<AppDatabase>().clientDao() }

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
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(CepService::class.java) as CepService
    }
}