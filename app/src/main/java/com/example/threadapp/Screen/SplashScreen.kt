package com.example.threadapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.threadapp.R
import com.example.threadapp.viewmodel.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController,splashViewModel: SplashViewModel) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.threads_logo),
            contentDescription = "App Logo",
            modifier = Modifier.height(100.dp)
        )
        LaunchedEffect(true) {
            splashViewModel.showSplashScreen{
            if(FirebaseAuth.getInstance().currentUser != null){
                println(FirebaseAuth.getInstance())
                navController.navigate(Screens.Home.route){
                    popUpTo(Screens.Splash.route){
                        inclusive = true
                    }
                }
            }else{
                navController.navigate(Screens.SignUp.route){
                    popUpTo(Screens.Splash.route){
                        inclusive = true
                    }
                }

            }
//            navController.navigate(Screens.BottomNavigation.route)
        }
        }
    }
}