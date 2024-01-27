package com.example.jetweatherforecast.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherAppBar(
    title: String="Title",
    icon:ImageVector?=null,
    isMainScreen:Boolean=true,
    elevation: Dp =0.dp,
    onAddActionClicked:()->Unit={},
    onButtonClicked:()->Unit={}
){
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onSecondary,
        style = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 15.sp)
    )
}