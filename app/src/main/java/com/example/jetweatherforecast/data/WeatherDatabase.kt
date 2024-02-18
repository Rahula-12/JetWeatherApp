package com.example.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.model.Measurement

@Database(entities = [Favourite::class,Measurement::class], version = 2, exportSchema = false)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao():FavouriteDao
}