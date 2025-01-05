package com.example.threadapp.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Home( navController: NavController){
    Scaffold(
        bottomBar = {myBottomBar(navController)}
    ){
        LazyColumn(modifier= Modifier.padding(it)){

        }
    }
    Text(text = "Home")
}