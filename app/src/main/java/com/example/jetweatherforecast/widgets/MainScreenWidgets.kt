package com.example.jetweatherforecast.widgets

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals

@Composable
fun HumidityWindPressureRow(data: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon", modifier = Modifier.size(20.dp)
            )
            Text(text = " ${data.list[0].humidity}%", style = MaterialTheme.typography.bodySmall)
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon", modifier = Modifier.size(20.dp)
            )
            Text(text = " ${data.list[0].pressure} psi", style = MaterialTheme.typography.bodySmall)
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon", modifier = Modifier.size(20.dp)
            )
            Text(text = " ${data.list[0].speed} mph", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun WeatherImage(weatherImageUrl: String="https://openweathermap.org/img/wn/01d.png") {
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
//    Surface(
//        shape = CircleShape,
//        modifier = Modifier.size(10.dp)
//    ) {
    AndroidView(
        factory = {imageView},
        modifier = Modifier.size(80.dp)
    )
//    }
}

@Composable
fun SunriseAndSunsetRow(sunrise:Int,sunset:Int,modifier: Modifier = Modifier){
    Row(
        modifier= modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise", modifier = modifier.size(30.dp),tint= MaterialTheme.colorScheme.onBackground)
            Text(text = formatDateTime(sunrise))
        }
        Row {
            Text(text = formatDateTime(sunset))
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset", modifier = modifier.size(30.dp))
        }
    }
}

@Composable
fun WeekDetailsColumn(modifier: Modifier = Modifier, weekDetails:List<WeatherItem>){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 5.dp,
                end = 5.dp
            )
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val restWeekDetails=weekDetails.subList(1,weekDetails.size)
        Text("This Week", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
        LazyColumn{
            items(restWeekDetails){
                WeekWeatherRow(it,modifier,"https://openweathermap.org/img/wn/${it.weather[0].icon}.png")
            }
        }
    }
}

@Composable
fun WeekWeatherRow(weatherItem: WeatherItem, modifier: Modifier, weatherImageUrl: String){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(formatDate(weatherItem.dt).substring(0,3), color = Color.Black)
        WeatherImage(weatherImageUrl)
        Text(
            weatherItem.weather[0].description,color = Color.Black,
            modifier = modifier.clip(RoundedCornerShape(20.dp)).background(color = Color(0xFFFFC400)).padding(5.dp)
        )
        Row {
            Text(text= buildAnnotatedString {
                withStyle(style = SpanStyle(color= Color.Blue, fontWeight = FontWeight.SemiBold)){
                    append("${formatDecimals(weatherItem.temp.max)}° ")
                }

                withStyle(style = SpanStyle(color= Color.LightGray,fontWeight = FontWeight.SemiBold)){
                    append("${formatDecimals(weatherItem.temp.min)}°")
                }
            })
        }
    }
}