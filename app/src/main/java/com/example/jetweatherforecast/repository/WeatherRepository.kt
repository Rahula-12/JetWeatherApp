package com.example.jetweatherforecast.repository

import android.util.Log
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
            Log.d("Repo",e.toString())
            return DataOrException(
                exception = e
            )
        }
        Log.d("Data",response.toString())
        return DataOrException(data = response,loading = false)
    }
}