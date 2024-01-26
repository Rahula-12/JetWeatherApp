package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
   private val weatherApi: WeatherApi
) {
    suspend fun getWeather(query:String):DataOrException<Weather,Boolean,Exception> {
        val response= try {
            weatherApi.getWeather(query)
        }
        catch (e:Exception) {
            return DataOrException<Weather,Boolean,Exception>(
                exception = e
            )
        }
        return DataOrException(data = response,loading = false)
    }
}