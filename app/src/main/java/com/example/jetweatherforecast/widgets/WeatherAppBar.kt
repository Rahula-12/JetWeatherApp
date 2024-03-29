package com.example.jetweatherforecast.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.favourite.FavouriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    favouriteViewModel: FavouriteViewModel= hiltViewModel(),
    title: String="Title",
    icon:ImageVector?=null,
    isMainScreen:Boolean=true,
    elevation: Dp =0.dp,
    onAddActionClicked:()->Unit={},
    navController: NavController,
    onButtonClicked:()->Unit={},
){
    val showToast= remember {
        mutableStateOf(false)
    }
    val showDialog= remember {
        mutableStateOf(false)
    }
    if(showDialog.value){
        ShowDropDownMenu(showDialog, navController)
    }
    Surface(shape= RectangleShape,shadowElevation=elevation) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier=Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            actions = {
                      if(isMainScreen){
                          IconButton(onClick = onAddActionClicked) {
                              Icon(
                                  imageVector = Icons.Default.Search,
                                  contentDescription = "Search Icon"
                              )
                          }
                          IconButton(onClick = { showDialog.value=!showDialog.value }) {
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
                if(isMainScreen){
                    val cityAndCountry=title.split(", ")
                    val isPresent=favouriteViewModel.favouriteList.collectAsState().value.filter {
                        it.city==cityAndCountry[0]
                    }
                    if(isPresent.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red.copy(alpha = 0.6f),
                            modifier = Modifier.clickable {
                                favouriteViewModel.insertFavourite(
                                    Favourite(
                                        cityAndCountry[0],
                                        cityAndCountry[1]
                                    )
                                ).run {
                                    showToast.value=true
                                }
                            }
                        )
                    }
                    else {showToast.value=false
                        Box{}}
                    if(showToast.value) Toast.makeText(LocalContext.current,"Added to Favourites",Toast.LENGTH_SHORT).show()
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
            ),

            )
    }
}

@Composable
fun ShowDropDownMenu(showDialog:MutableState<Boolean>,navController: NavController){
    val iconList:List<ImageVector> = listOf(Icons.Default.FavoriteBorder,Icons.Default.Settings,Icons.Rounded.Info)
    val descriptions:List<String> = listOf("Favourites","Settings","About")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(expanded = showDialog.value, onDismissRequest = {
            showDialog.value=false
        },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
            descriptions.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier.padding(5.dp)
                ){
                    Icon(
                        imageVector = iconList[index],
                        contentDescription = s,
                        modifier = Modifier.weight(1f),
                        tint = Color.Gray
                    )
                    Text(
                        text = s,
                        color=Color.Gray,
                        modifier = Modifier
                            .weight(2f)
                            .clickable {
                                when (index) {
                                    0 -> navController.navigate(WeatherScreens.FavouriteScreen.name)
                                    1 -> navController.navigate(WeatherScreens.SettingsScreen.name)
                                    else -> navController.navigate(WeatherScreens.AboutScreen.name)
                                }
                            }
                        )
                }
            }
        }
    }
}