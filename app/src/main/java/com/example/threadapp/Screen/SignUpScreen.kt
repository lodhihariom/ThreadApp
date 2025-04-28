package com.example.threadapp.Screen

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.threadapp.R
import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberAsyncImagePainter
import com.example.threadapp.ImageUpLoading.uploadImageToCloudinary
//import coil3.compose.rememberAsyncImagePainter
import com.example.threadapp.viewmodel.AuthViewModel


//import com.example.threadapp.ViewModel.AuthViewModel


@Composable
fun SignUpScreen( navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var imageRef by remember { mutableStateOf<Uri?>(null) }
        var context = LocalContext.current
        var firebaseUser = authViewModel.firebaseUser.observeAsState(null)

        val requestToPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        var laucher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                imageRef = uri
            }

        //permissionLauncher
        var permissionLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {

                } else {

                }

            }


        Text(
            "Register",
            style = TextStyle(
                fontSize = 30.sp
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = if (imageRef == null) painterResource(id = R.drawable.profile_image)
            else rememberAsyncImagePainter(imageRef),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .clickable {
                    var isGranted = ContextCompat.checkSelfPermission(
                        context,
                        requestToPermission
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        laucher.launch("image/*")
                    } else {
                        permissionLauncher.launch(requestToPermission)
                    }
                },
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(15.dp))


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter name...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter username...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter email...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password...") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Button(onClick = {
            if(name.isEmpty() || username.isEmpty()|| email.isEmpty()|| password.isEmpty() || imageRef==null){
                Toast.makeText(context,"Fill the details completely!!" , Toast.LENGTH_LONG).show()
            }else{
                if (imageRef!=null){
                    uploadImageToCloudinary(context, imageRef!!,"thread"){
                            url ->
                        authViewModel.Register(email, password, name, username,url,context)
                    }
                }
                if (firebaseUser.value != null){
                    navController.navigate(Screens.Home.route)
                }
            }
        }) {
            Text("submit")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Already have an account? login",
            style = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier.clickable {
                navController.navigate(Screens.Login.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}