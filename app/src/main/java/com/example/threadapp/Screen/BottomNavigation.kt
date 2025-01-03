package com.example.threadapp.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavController){
    Scaffold (
        bottomBar = { MyBottomBar(navController = navController)}
    ){
        LazyColumn (modifier = Modifier.padding(it)){

        }
    }
}

@Composable
fun MyBottomBar(navController: NavController) {
     val backStack = navController.currentBackStackEntryAsState().value
}
