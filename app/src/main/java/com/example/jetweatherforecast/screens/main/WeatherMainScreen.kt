package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.widgets.WeatherAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel= hiltViewModel()
){
    val weather= produceState(initialValue = DataOrException<Weather,Boolean,Exception>(), producer = {
        value=mainViewModel.getWeather("Amritsar")
    }).value
    if(weather.loading == true){
        CircularProgressIndicator()
    }
    else if(weather.data!=null){
        MainScaffold(weather.data!!)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScaffold(weather:Weather) {
    Scaffold(
        topBar = {
            WeatherAppBar(title = weather.city.name)
        }
    ) {
        MainContent(data=weather,modifier=Modifier.padding(it))
    }
}

@Composable
fun MainContent(data: Weather,modifier: Modifier=Modifier) {
    Text(text = data.city.name)
}
