package com.example.threadapp.Navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.AddThreadScreen
import com.example.threadapp.Screen.Home
import com.example.threadapp.Screen.LoginScreen
import com.example.threadapp.Screen.NotificationScreen
import com.example.threadapp.Screen.ProfileScreen
import com.example.threadapp.Screen.Screens
import com.example.threadapp.Screen.SearchScreen
import com.example.threadapp.Screen.SignUpScreen
import com.example.threadapp.Screen.SplashScreen
import com.example.threadapp.Screen.bottomNavigation

@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screens.Login.route){
            LoginScreen(navController)
        }
        composable(Screens.SignUp.route){
            SignUpScreen(navController)
        }
        composable(Screens.Home.route) {
           Home(navController)
        }
        composable(Screens.Profile.route){
            ProfileScreen()
        }
        composable(Screens.Search.route){
             SearchScreen()
        }
        composable(Screens.AddThread.route){
         AddThreadScreen()
        }
        composable(Screens.Notification.route){
            NotificationScreen()
        }

    }
}
//@Composable
//fun navigation(){
//    val navController = rememberNavController();
//    NavHost(navController = navController,
//        startDestination = Screens.Splash.route) {
//        composable(Screens.Splash.route){
//            splash(navController = navController)
//        }
//        composable(Screens.BottomNavigation.route){
//            bottomNavigation(navController)
//        }
//        composable(Screens.Home.route){
//            home(navController = navController)
//        }
//        composable(Screens.Profile.route){
//            profle(navController = navController)
//        }
//        composable(Screens.Notification.route){
//            notification(navController = navController)
//        }
//        composable(Screens.Search.route){
//            search(navController = navController)
//        }
//        composable(Screens.AddThread.route){
//
//        }
//
//    }
//}