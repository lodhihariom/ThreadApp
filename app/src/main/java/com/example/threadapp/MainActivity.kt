package com.example.threadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController

import com.example.threadapp.Navigation.Navigation
import com.example.threadapp.Screen.SplashScreen

import com.example.threadapp.ui.theme.ThreadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ThreadAppTheme {
                Navigation()
//                SplashScreen(navController)
            }
            }
        }
}
