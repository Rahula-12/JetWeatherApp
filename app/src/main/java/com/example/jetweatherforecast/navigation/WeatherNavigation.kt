package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweatherforecast.screens.about.AboutScreen
import com.example.jetweatherforecast.screens.favourite.FavouriteScreen
import com.example.jetweatherforecast.screens.main.WeatherMainScreen
import com.example.jetweatherforecast.screens.search.SearchScreen
import com.example.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController= rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name+"/{city}",
            arguments= listOf(navArgument(name="city"){
                type= NavType.StringType
            })
        ){
            WeatherMainScreen(navController = navController,city=it.arguments?.getString("city"))
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController=navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(modifier = Modifier,navController)
        }
        composable(WeatherScreens.FavouriteScreen.name){
            FavouriteScreen(modifier = Modifier, navController = navController)
        }
    }
}