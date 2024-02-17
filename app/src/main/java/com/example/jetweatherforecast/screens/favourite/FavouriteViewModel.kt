package com.example.jetweatherforecast.screens.favourite

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.repository.WeatherDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository):ViewModel() {
    private val _favouriteList:MutableStateFlow<List<Favourite>> = MutableStateFlow(emptyList())
    val favouriteList=_favouriteList.asStateFlow()

    init {
        viewModelScope.launch {
            weatherDBRepository.getAllFavourites().collectLatest {
                if(it.isNullOrEmpty())  Log.d("Empty","Empty")
                else Log.d("Favs",it.toString())
                _favouriteList.value=it
            }
        }
    }

    fun deleteFavourite(favourite: Favourite) {
        viewModelScope.launch {
            weatherDBRepository.deleteFavourite(favourite)
        }
    }

    fun insertFavourite(favourite: Favourite) {
        viewModelScope.launch {
            weatherDBRepository.insertFavourite(favourite)
        }
    }

    fun getFavouriteByCity(city:String) {
        viewModelScope.launch {
            weatherDBRepository.getFavouriteByCity(city)
        }
    }

}