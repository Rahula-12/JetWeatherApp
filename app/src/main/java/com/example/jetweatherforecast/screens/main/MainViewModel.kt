package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel(){
    val dataOrException:MutableState<DataOrException<Weather,Boolean,Exception>> = mutableStateOf(
        DataOrException(data = null,loading = true, exception = Exception("")))

    init {
        getWeather("Amritsar")
    }

    private fun getWeather(cityQuery:String) {
        viewModelScope.launch {
            if(cityQuery.isEmpty()) return@launch
            dataOrException.value.loading=true
            dataOrException.value=repository.getWeather(cityQuery)
            if(!dataOrException.value.data.toString().isNullOrEmpty()) dataOrException.value.loading=false
        }
        Log.d("Temp",dataOrException.value.data.toString())
    }
}