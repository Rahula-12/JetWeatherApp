package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.jetweatherforecast.MainActivity
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.favourite.FavouriteViewModel
import com.example.jetweatherforecast.screens.settings.SettingsViewModel
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunriseAndSunsetRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherImage
import com.example.jetweatherforecast.widgets.WeekDetailsColumn

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel= hiltViewModel(),
    city: String?
){
    val measurementList=settingsViewModel.measurementList.collectAsState().value
    val measurement= remember {
        mutableStateOf("imperial")
    }
    val isImperial= remember {
        mutableStateOf(true)
    }
    if(!measurementList.isNullOrEmpty()) {
        measurement.value=measurementList[0].measurement.split(" ")[0].lowercase()
        isImperial.value=measurement.value=="imperial"
    val weather= produceState(initialValue = DataOrException<Weather,Boolean,Exception>(), producer = {
        value=mainViewModel.getWeather(city!!,measurement=measurement.value)
    }).value
        if (weather.loading == true) {
            CircularProgressIndicator()
        } else if (weather.data != null) {
            MainScaffold(weather.data!!, navController,isImperial=isImperial.value)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    ViewModelProvider(LocalContext.current as MainActivity)[FavouriteViewModel::class.java]
    Scaffold(
        topBar = {
            WeatherAppBar(title = "${weather.city.name}, ${weather.city.country}", elevation = 5.dp, navController=navController, onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            })
        }

    ) {
        MainContent(data=weather,
            isImperial=isImperial
            //modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun MainContent(data: Weather,modifier: Modifier=Modifier,isImperial: Boolean) {
    val weatherImageUrl="https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Log.d("url",weatherImageUrl)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text= formatDate(data.list[0].dt),
            style=MaterialTheme.typography.titleSmall,
            color=MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(
                top=20.dp,
                bottom = 10.dp
            )
        )
        Surface(
            color = Color(0xFFFFC400),
            shape = CircleShape,
            modifier = modifier
                .size(220.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WeatherImage(weatherImageUrl)
                Text(text= formatDecimals(data.list[0].temp.day)+"°", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text=data.list[0].weather[0].description, style = MaterialTheme.typography.headlineMedium, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(data,isImperial=isImperial)
        Divider()
        SunriseAndSunsetRow(data.list[0].sunrise,data.list[0].sunset)
        WeekDetailsColumn(weekDetails = data.list)
    }
}

