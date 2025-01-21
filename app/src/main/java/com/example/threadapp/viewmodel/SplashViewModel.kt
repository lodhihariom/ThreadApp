package com.example.threadapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel:ViewModel() {
    var isSplashScreenVisible = false
    fun showSplashScreen(onSplashFinished: () -> Unit){
        if (!isSplashScreenVisible){
            viewModelScope.launch {
                delay(2000)
                isSplashScreenVisible = true
                onSplashFinished()
            }
        }else{
            onSplashFinished()
        }
    }

}