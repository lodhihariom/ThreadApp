package com.example.threadapp.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SignUpScreen( navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var userName by remember {mutableStateOf("")  }
        var Name by remember { mutableStateOf("") }
        var email by remember {mutableStateOf("")  }
        var password by remember { mutableStateOf("") }
        Text("Sign Up", style = TextStyle(fontSize = 30.sp))
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )
        OutlinedTextField(
            value = Name,
            onValueChange = {Name = it},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = userName,
            onValueChange = {userName = it},
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Submit")
        }
        Text("Already have an account? Login", style = TextStyle(
            fontSize = 15.sp
        ),modifier = Modifier.clickable {
            navController.navigate(Screens.Login.route){

            }
        })
    }
}