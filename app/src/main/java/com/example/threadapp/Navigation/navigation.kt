package com.example.threadapp.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import com.example.threadapp.Screen.LoginScreen
import com.example.threadapp.Screen.OtherProfileScreen

import com.example.threadapp.Screen.ProfileScreen
import com.example.threadapp.Screen.Screens

import com.example.threadapp.Screen.SignUpScreen
import com.example.threadapp.Screen.SplashScreen
import com.example.threadapp.Screen.addThread
import com.example.threadapp.Screen.home
import com.example.threadapp.Screen.notification
import com.example.threadapp.Screen.search
import com.example.threadapp.ViewModel.HomeViewModel
import com.example.threadapp.ViewModel.SearchViewModel
import com.example.threadapp.ViewModel.UserViewModel
import com.example.threadapp.viewmodel.AuthViewModel
import com.example.threadapp.viewmodel.SplashViewModel
import com.example.threadapp.viewmodel.ThreadViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val splashViewModel: SplashViewModel = viewModel()
    val threadViewModel: ThreadViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController, splashViewModel = splashViewModel)
        }
        composable(Screens.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(Screens.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }
        composable(Screens.Home.route) {
            home(navController, homeViewModel)
        }
        composable(Screens.Profile.route) {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userViewModel
            )
        }
        composable(Screens.Search.route) {
            search(navController, searchViewModel)
        }
        composable(Screens.AddThread.route) {
            addThread(navController, threadViewModel)
        }
        composable(Screens.Notification.route) {
            notification(navController)
        }
        composable(Screens.OtherProfile.route + "/{id}") {
            val id = it.arguments!!.get("id").toString()
            println("id" + id)
            id?.let {
                OtherProfileScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    userViewModel = userViewModel,
                    id
                )
            }

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