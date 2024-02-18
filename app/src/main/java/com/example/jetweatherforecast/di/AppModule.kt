package com.example.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.jetweatherforecast.data.WeatherDatabase
import com.example.jetweatherforecast.network.WeatherApi
import com.example.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesWeatherApi():WeatherApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun providesFavouriteDao(weatherDatabase: WeatherDatabase)=weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun providesWeatherDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_db"
        ).
        fallbackToDestructiveMigration().
    build()

}