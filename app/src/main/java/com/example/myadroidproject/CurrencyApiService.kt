package com.example.myadroidproject

import retrofit2.http.GET

interface CurrencyApiService {
    @GET("cad.json")
    suspend fun getCurrencies():Currency

}