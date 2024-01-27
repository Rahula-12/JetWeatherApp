package com.example.jetweatherforecast.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel= hiltViewModel()
){
    ShowData(mainViewModel)
}

@Composable
private fun ShowData(mainViewModel: MainViewModel) {
    val weather= mainViewModel.dataOrException
    if(weather.value.loading == true){
            CircularProgressIndicator()
    }
    else{
        Text(text = "MainScreen ${mainViewModel.dataOrException.value.data!!.city}")
    }
}