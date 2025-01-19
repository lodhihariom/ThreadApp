package com.example.threadapp.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Home( navController: NavController){
    Scaffold(
        bottomBar = {myBottomBar(navController)}
    ){
        LazyColumn(modifier= Modifier.padding(it)){

        }
    }

}

@Composable
fun ThreadItem(thread: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

    }
}
fun getSampleThreads(): List<String> {
    return listOf(
        "Thread 1",
        "Thread 2",
        "Thread 3",
        "Thread 4",
        "Thread 5",
        "Thread 6",
        "Thread 7",
        "Thread 8",
        "Thread 9",
        "Thread 10",
    )
}
