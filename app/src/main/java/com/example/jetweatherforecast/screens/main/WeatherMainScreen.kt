package com.example.jetweatherforecast.screens.main

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel= hiltViewModel()
){
    val weather= produceState(initialValue = DataOrException<Weather,Boolean,Exception>(), producer = {
        value=mainViewModel.getWeather("Jalandhar")
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
            WeatherAppBar(title = "${weather.city.name}, ${weather.city.country}", elevation = 5.dp, navController=navController)
        }

    ) {
        MainContent(data=weather,modifier=Modifier.padding(it))
    }
}

@Composable
fun MainContent(data: Weather,modifier: Modifier=Modifier) {
    val weatherImageUrl="https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Log.d("url",weatherImageUrl)
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text="Jan 29",
            style=MaterialTheme.typography.titleSmall,
            color=MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(6.dp)
        )
        Surface(
            color = Color(0xFFFFC400),
            shape = CircleShape,
            modifier = modifier
                .size(220.dp)
                .padding(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WeatherImage(weatherImageUrl)
                Text(text="56", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text="Snow", style = MaterialTheme.typography.headlineMedium, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Preview
@Composable
private fun WeatherImage(weatherImageUrl: String="https://openweathermap.org/img/wn/01d.png") {
//    Surface(
//        shape = CircleShape,
//        modifier = Modifier.size(10.dp)
//    ) {
//        AsyncImage(model = weatherImageUrl, contentDescription = "weather image")
//    }
    val context= LocalContext.current
    val imageView= remember {
        ImageView(context)
    }
    Glide.with(context).load(weatherImageUrl).into(imageView)
    AndroidView(factory = {imageView})
}

