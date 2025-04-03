package com.example.myadroidproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/"
   val api:CurrencyApiService by lazy {
      Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(CurrencyApiService::class.java)
       //"date": "2024-04-02",
       //  "cad": {
       //    "usd": 0.74,
       //    "eur": 0.68,
       //    "isk": 101.9,

   }
}