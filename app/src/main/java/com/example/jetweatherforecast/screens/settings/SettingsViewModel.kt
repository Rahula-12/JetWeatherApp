package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.model.Measurement
import com.example.jetweatherforecast.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository):ViewModel() {
    private val _measurementList: MutableStateFlow<List<Measurement>> = MutableStateFlow(emptyList())
    val measurementList=_measurementList.asStateFlow()

    init {
        viewModelScope.launch {
            weatherDBRepository.getAllMeasurements().collectLatest {
                _measurementList.value=it
            }
        }
    }

    fun deleteMeasurement(measurement: Measurement) {
        viewModelScope.launch {
            weatherDBRepository.deleteMeasurement(measurement)
        }
    }

    fun insertMeasurement(measurement: Measurement) {
        viewModelScope.launch {
            weatherDBRepository.insertMeasurement(measurement)
        }
    }

    fun deleteAllMeasurements() {
        viewModelScope.launch {
            weatherDBRepository.deleteAllMeasurements()
        }
    }
}