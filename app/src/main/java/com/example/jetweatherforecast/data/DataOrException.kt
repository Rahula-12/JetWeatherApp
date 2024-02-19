package com.example.jetweatherforecast.data

data class DataOrException<T,Boolean,E:Exception>(
    var data:T?=null,
    var loading: kotlin.Boolean?=null,
    var exception: E?=null
)