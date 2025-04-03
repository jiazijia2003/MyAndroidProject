package com.example.myadroidproject.services

import com.example.myadroidproject.models.Currency
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("cad.json")
    suspend fun getCurrencies(): Currency

}