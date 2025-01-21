package com.example.threadapp.Screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.User
import com.example.threadapp.Model.post
import com.example.threadapp.R
//import com.example.threadapp.ViewModel.AuthViewModel
import com.example.threadapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlin.contracts.contract

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var firebaseUser = authViewModel.firebaseUser.observeAsState()
    var postList = authViewModel.posts.observeAsState(emptyList()).value

    LaunchedEffect(firebaseUser) {
        firebaseUser?.let {
            authViewModel.fetchPosts(it.value!!.uid)
        }
    }
    var context = LocalContext.current
    var imageRef by remember {
        mutableStateOf<Uri?>(null)
    }
    val requestToPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var launcher =
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
    Scaffold(
        topBar = { topBar() },
        bottomBar = { myBottomBar(navController) },

    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                ProfileContent(
                    name = SharedPref.getName(context),
                    username = SharedPref.getUserName(context),
                    imageRef = imageRef,
                    onImageClick = {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context,
                            requestToPermission
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(requestToPermission)
                        }
                    },
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
            items(postList){
                post ->
                PostItem(posts = post)
            }

        }

    }

}


//    Column() {
//        topBar()
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            Column {
//                Text("")
//                Text("")
//            }
//            Image(painter = painterResource(R.drawable.profile_image),
//                contentDescription = "profile image",
//                modifier = Modifier.size(70.dp).clip(CircleShape)
//                    .clickable {
//                        var isGranted = ContextCompat.checkSelfPermission(
//                            context,
//                            requestToPermission
//                        ) == PackageManager.PERMISSION_GRANTED
//
//                        if (isGranted) {
//                            laucher.launch("image/*")
//                        } else {
//                            permissionLauncher.launch(requestToPermission)
//                        }
//                    }
//            )
//        }
//    }
//}
@Composable
fun topBar(){
    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        IconButton(
            onClick = {},
            modifier = Modifier.size(30.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_insert_chart_outlined_24),
                contentDescription = "chart")
        }
        IconButton(onClick = {},
            modifier = Modifier.size(30.dp)) {
            Image(painter = painterResource(id = R.drawable.instagram),
                contentDescription = "back")
        }
        IconButton(
            onClick = {},
            modifier = Modifier.size(30.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.menu),
                contentDescription = "settings")
        }

    }
}

@Composable
fun ProfileContent(
    imageRef: Uri?,
    onImageClick: () -> Unit,
    name: String,
    username: String,
    authViewModel: AuthViewModel,
    navController: NavController

) {
    var firebaseUser = authViewModel.firebaseUser.observeAsState()
    Row(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = username,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Image(
            painter = if (imageRef != null) rememberImagePainter(imageRef) else painterResource(R.drawable.profile_image),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { onImageClick(

                ) }
        )

    }
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)) {
            Text(text = "Edit Profile")
        }
        Button(onClick = {
            authViewModel.signOut()
            if ( firebaseUser.value == null){
            navController.navigate("login")}
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun PostItem(posts: post) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = posts.postUserImage, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Posted on: ${posts.postTime}", style = MaterialTheme.typography.bodySmall)
    }
}
