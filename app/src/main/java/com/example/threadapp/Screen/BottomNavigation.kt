package com.example.threadapp.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.threadapp.Model.bottomIcon

@Composable
fun bottomNavigation(navController: NavController){
    Scaffold(
        bottomBar = {myBottomBar(navController)}
    ){
        LazyColumn(modifier= Modifier.padding(it)){

        }
    }
}

@Composable
fun myBottomBar(navController: NavController){
    val backStack = navController.currentBackStackEntryAsState().value

    val listOfIcon = listOf(
        bottomIcon("home",Screens.Home.route,Icons.Default.Home),
        bottomIcon("notification",Screens.Notification.route,Icons.Default.Notifications),
        bottomIcon("Add Thread",Screens.AddThread.route,Icons.Default.Add),
        bottomIcon("Search",Screens.Search.route,Icons.Default.Search),
        bottomIcon("profile",Screens.Profile.route,Icons.Default.AccountCircle),
    )

    BottomAppBar {
        listOfIcon.forEach {
            val select = it.route ==  backStack?.destination?.route
            NavigationBarItem(
                selected = select,
                onClick = {
                    navController.navigate(it.route)
                },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = it.title)
                })
        }
    }
}