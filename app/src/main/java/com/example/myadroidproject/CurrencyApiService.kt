package com.example.myadroidproject

interface CurrencyApiService {
    suspend fun getCurrencies():List<Currency>
}