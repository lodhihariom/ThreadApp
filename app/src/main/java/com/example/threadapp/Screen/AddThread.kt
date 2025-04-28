package com.example.threadapp.Screen

import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.threadapp.ImageUpLoading.uploadImageToCloudinary
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.R
import com.example.threadapp.viewmodel.ThreadViewModel
import com.google.firebase.auth.FirebaseAuth

//import java.time.format.TextStyle

@Composable
fun addThread(
    navController: NavController,
    threadViewModel: ThreadViewModel
) {
    var context = LocalContext.current
    var profileImg by remember {mutableStateOf("")}
    Scaffold(
        bottomBar = { myBottomBar(navController) }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {

        }
    }



    var caption by remember {
        mutableStateOf("")
    }
    var imageRef by remember {
        mutableStateOf<Uri?>(null)
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "Add Thread",
            style = TextStyle(
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            println("image " + SharedPref.getImage(context))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(SharedPref.getImage(context)).build(),
                contentDescription = "Profile Image",
                placeholder = painterResource(id = R.drawable.profile_image),
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = SharedPref.getName(context),
                style = TextStyle(
                    fontSize = 22.sp
                )
            )
        }

        OutlinedTextField(value = caption,
            onValueChange = { caption = it },
            label = { Text("Write caption here.....") }
        )
        Spacer(modifier = Modifier.height(15.dp))

        if (imageRef == null) {
            Image(painter = painterResource(id = R.drawable.baseline_attach_file_24),
                contentDescription = null,
                modifier = Modifier.clickable {
                    var isGranted = ContextCompat.checkSelfPermission(
                        context,
                        requestToPermission
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        laucher.launch("image/*")
                    } else {
                        permissionLauncher.launch(requestToPermission)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageRef).build(),
            contentDescription = "Profile Image",
            placeholder = painterResource(id = R.drawable.profile_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))


        Button(onClick = {
            if (imageRef == null && caption.isEmpty()) {
                Toast.makeText(context, "make thread first", Toast.LENGTH_LONG).show()
            } else {
                if (imageRef!=null){
                    uploadImageToCloudinary(context, imageRef!!,"thread"){
                        threadViewModel.saveThread(
                            FirebaseAuth.getInstance().currentUser?.uid,
                            it,
                            caption
                        ) {
                            if (it){
                                Toast.makeText(context,"thread added successfully!",Toast.LENGTH_LONG).show()
                                navController.navigate(Screens.Home.route)
                            }
                        }
                    }
                }else{
                    threadViewModel.saveThread(
                        uid = FirebaseAuth.getInstance().currentUser?.uid,
                        thread = caption
                    ) {
                        if (it){
                            Toast.makeText(context,"thread added successfully!",Toast.LENGTH_LONG).show()
                            navController.navigate(Screens.Home.route)
                        }
                    }
                }
            }
        }) {
            Text("Post")
        }
    }
}