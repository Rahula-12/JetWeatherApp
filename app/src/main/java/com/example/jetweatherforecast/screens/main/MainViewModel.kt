package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    private val _dataOrException:MutableState<DataOrException<Weather,Boolean,Exception>> = mutableStateOf(
        DataOrException(data = null,loading = true, exception = Exception(""))
    )
    val dataOrException:State<DataOrException<Weather,Boolean,Exception>> = _dataOrException
    init {
        getWeather("Amritsar")
    }

    private fun getWeather(cityQuery:String) {
        viewModelScope.launch {
            if(cityQuery.isEmpty()) return@launch
            _dataOrException.value.loading=true
            _dataOrException.value=repository.getWeather(cityQuery)
            if(!_dataOrException.value.data.toString().isNullOrEmpty()) _dataOrException.value.loading=false
            Log.d("ScopeData",_dataOrException.value.data.toString())
        }
        Log.d("ViewModelData",_dataOrException.value.data.toString())
    }
}