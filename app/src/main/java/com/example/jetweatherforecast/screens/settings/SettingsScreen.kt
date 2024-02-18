package com.example.jetweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel= hiltViewModel(),
    navController: NavController
){
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Settings Screen",
                icon= Icons.Default.ArrowBack,
                isMainScreen = false
            )
        }
    ) {
        val unitToggleState= remember {
            mutableStateOf(false)
        }
        val measurementList= arrayOf("Imperial (F)","Metric (C)")
        val choiceState= remember {
            mutableStateOf("")
        }
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Change Units of Measurement", modifier = Modifier.padding(bottom = 15.dp))
                IconToggleButton(checked = !unitToggleState.value, onCheckedChange = {bool->
                    unitToggleState.value=!bool
                    if(unitToggleState.value) choiceState.value="Imperial (F)"
                    else choiceState.value="Metric (C)"
                },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            Color.Magenta.copy(alpha = 0.5f)
                        )) {
                            Text(text = if(unitToggleState.value) "Fahrenheit °F" else "Celsius °C")
                }
            }
        }
    }
}