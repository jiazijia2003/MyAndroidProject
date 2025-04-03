package com.example.myadroidproject.models

import java.io.Serializable

data class Expense (val name:String,
    val amount:String,
    val date:String,
    val currency: String,
    val convertedCost:Double):Serializable