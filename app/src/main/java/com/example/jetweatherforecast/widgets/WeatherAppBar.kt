package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String="Title",
    icon:ImageVector?=null,
    isMainScreen:Boolean=true,
    elevation: Dp =0.dp,
    onAddActionClicked:()->Unit={},
    onButtonClicked:()->Unit={},
    navController: NavController
){
    Surface(shape= RectangleShape,shadowElevation=elevation) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            },
            actions = {
                      if(isMainScreen){
                          IconButton(onClick = { /*TODO*/ }) {
                              Icon(
                                  imageVector = Icons.Default.Search,
                                  contentDescription = "Search Icon"
                              )
                          }
                          IconButton(onClick = { /*TODO*/ }) {
                              Icon(imageVector = Icons.Default.MoreVert,
                                  contentDescription = "More options")
                          }
                      }
            }, navigationIcon = {
                     if(icon!=null){
                         Icon(
                             imageVector = icon,
                             contentDescription = null,
                             tint = MaterialTheme.colorScheme.onSecondary,
                             modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             }
                             )
                     }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
            ),

            )
    }
}