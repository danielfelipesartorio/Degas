package com.sartorio.degas.ui

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    @GET("/ws/{cep}/json/")
    suspend fun getAddressDetails(@Path("cep") cep: String): Response<AddressDto>
}