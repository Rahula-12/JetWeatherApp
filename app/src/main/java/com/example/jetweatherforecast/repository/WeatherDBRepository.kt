package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.FavouriteDao
import com.example.jetweatherforecast.model.Favourite
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val favouriteDao: FavouriteDao) {

    suspend fun insertFavourite(favourite: Favourite)=favouriteDao.insertFavourite(favourite)

    suspend fun deleteFavourite(favourite: Favourite)=favouriteDao.deleteFavourite(favourite)

    fun getAllFavourites()=favouriteDao.getAllFavourites()

    suspend fun deleteAllFavourites()=favouriteDao.deleteAllFavourites()

    suspend fun getFavouriteByCity(city:String)=favouriteDao.getFavouriteByCity(city)

}