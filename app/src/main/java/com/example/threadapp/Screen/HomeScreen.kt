package com.example.threadapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.threadapp.Model.post
import com.example.threadapp.R
import com.example.threadapp.viewmodel.AuthViewModel

@Composable
fun Home( navController: NavController,
          authViewModel: AuthViewModel){
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("For You","Following")
    Scaffold(
        bottomBar = {myBottomBar(navController)}
    ){

        Column(modifier = Modifier.padding(it)) {
            Image(
                painter = painterResource(id = R.drawable.threads),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            )
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> ForYouTab(authViewModel)
                1 -> FollowingTab(authViewModel)

            }
        }
    }

}

@Composable
fun FollowingTab(authViewModel: AuthViewModel) {
    val posts by authViewModel.posts.observeAsState(emptyList())
    LazyColumn {
        items(posts) { post ->
            PostCard(post)
        }
    }
}

@Composable
fun ForYouTab(authViewModel: AuthViewModel) {
    val posts by authViewModel.posts.observeAsState(emptyList())
    LazyColumn {
        items(posts) { post ->
            PostCard(post)
        }
    }
}
@Composable
fun PostCard(posts: post) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = posts.postUserName, style = MaterialTheme.typography.bodyLarge)
            Text(text = posts.postText, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Posted on: ${posts.postTime}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
