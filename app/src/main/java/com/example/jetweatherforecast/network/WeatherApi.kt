package com.example.jetweatherforecast.network

import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query(value = "q") query:String,
        @Query(value = "appId") appId:String=Constants.APP_ID,
        @Query(value = "units") units:String="imperial"
    ):Weather

}