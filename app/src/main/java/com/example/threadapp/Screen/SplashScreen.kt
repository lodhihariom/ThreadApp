package com.example.threadapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.thread_logo), contentDescription = "logo",modifier = Modifier.requiredSize(50.dp))
        LaunchedEffect(true) {
            delay(1500)
            if(FirebaseAuth.getInstance().currentUser != null){
                navController.navigate(Screens.Home.route)
            }else{
                navController.navigate(Screens.SignUp.route)
            }
//            navController.navigate(Screens.BottomNavigation.route)
        }
    }
}