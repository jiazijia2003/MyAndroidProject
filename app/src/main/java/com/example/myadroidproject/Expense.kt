package com.example.myadroidproject

import android.icu.util.Currency
import java.io.Serializable

data class Expense (val name:String,
    val amount:String,
    val date:String,
    val currency: Currency,
    val convertedCost:Double):Serializable