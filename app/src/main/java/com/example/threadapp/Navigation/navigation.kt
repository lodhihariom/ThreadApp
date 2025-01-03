package com.example.threadapp.Navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.Screens

@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
        }
        composable(Screens.Home.route) {

        }
        composable(Screens.Profile.route){

        }
        composable(Screens.Search.route){}
        composable(Screens.Thread.route){}
        composable(Screens.Notification.route){}
        composable(Screens.BottomNavigation.route){}

    }
}