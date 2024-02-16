package com.example.jetweatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import com.example.jetweatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Preview
@Composable
fun WeatherSplashScreen(
    navController: NavController= rememberNavController(),
    modifier: Modifier=Modifier
) {

    val scale= remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(2000)
        navController.navigate(WeatherScreens.MainScreen.name+"/Amritsar")
    })

    Surface(
        shape = CircleShape,
        border = BorderStroke(2.dp, color = Color.Gray),
        modifier = modifier.size(300.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise")
            Text(
                text = "Find the Sun?",
                modifier=modifier.padding(10.dp),
                color = Color.LightGray,
                 style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}