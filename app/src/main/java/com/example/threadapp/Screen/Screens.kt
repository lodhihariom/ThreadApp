package com.example.threadapp.Screen

sealed class Screens(val route:String){
    object Home : Screens("home")
    object Splash : Screens("splash")
    object Profile : Screens("profile")
//    object BottomNavigation : Screens("bottomNavigation")
    object Search : Screens("search")
    object AddThread : Screens("thread")
    object Notification : Screens("notification")
    object Login : Screens("login")
    object SignUp : Screens("signup")
    object OtherProfile : Screens("otherProfile")
}
