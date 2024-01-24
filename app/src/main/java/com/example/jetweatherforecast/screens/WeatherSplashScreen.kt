package com.example.jetweatherforecast.screens

import android.content.res.Resources.Theme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.R

@Preview
@Composable
fun WeatherSplashScreen(
    navController: NavController= rememberNavController(),
    modifier: Modifier=Modifier
) {
    Surface(
        shape = CircleShape,
        border = BorderStroke(2.dp, color = Color.Gray),
        modifier = modifier.size(200.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun")
            Text(
                text = "Find the Sun?",
                modifier=modifier.padding(10.dp),
                color = Color.LightGray,
                 style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}