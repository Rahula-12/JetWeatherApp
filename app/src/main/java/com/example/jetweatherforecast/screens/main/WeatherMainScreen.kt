package com.example.jetweatherforecast.screens.main

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunriseAndSunsetRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherImage
import com.example.jetweatherforecast.widgets.WeekDetailsColumn

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
        MainScaffold(weather.data!!,navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScaffold(weather:Weather,navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(title = "${weather.city.name}, ${weather.city.country}", elevation = 5.dp, navController=navController, onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            })
        }

    ) {
        MainContent(data=weather,
            //modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun MainContent(data: Weather,modifier: Modifier=Modifier) {
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
        HumidityWindPressureRow(data)
        Divider()
        SunriseAndSunsetRow(data.list[0].sunrise,data.list[0].sunset)
        WeekDetailsColumn(weekDetails = data.list)
    }
}

