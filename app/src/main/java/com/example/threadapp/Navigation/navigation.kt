package com.example.threadapp.Navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.AddThreadScreen
import com.example.threadapp.Screen.Home
import com.example.threadapp.Screen.LoginScreen
import com.example.threadapp.Screen.Notification
//import com.example.threadapp.Screen.NotificationScreen
import com.example.threadapp.Screen.ProfileScreen
import com.example.threadapp.Screen.Screens
import com.example.threadapp.Screen.SearchScreen
import com.example.threadapp.Screen.SignUpScreen
import com.example.threadapp.Screen.SplashScreen
import com.example.threadapp.Screen.bottomNavigation
import com.example.threadapp.viewmodel.AuthViewModel
import com.example.threadapp.viewmodel.SplashViewModel

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val splashViewModel: SplashViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController, splashViewModel = splashViewModel)
        }
        composable(Screens.Login.route){
            LoginScreen(navController, authViewModel)
        }
        composable(Screens.SignUp.route){
            SignUpScreen(navController, authViewModel)
        }
        composable(Screens.Home.route) {
           Home(navController, authViewModel)
        }
        composable(Screens.Profile.route){
            ProfileScreen( navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.Search.route){
             SearchScreen()
        }
        composable(Screens.AddThread.route){
         AddThreadScreen(navController)
        }
        composable(Screens.Notification.route){
            Notification()
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