package com.example.jetweatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favourite(
    @PrimaryKey
    val city:String,
    val country:String
)
