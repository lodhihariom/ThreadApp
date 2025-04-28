package com.example.threadapp.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.threadapp.Model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@SuppressLint("SuspiciousIndentation")
class SearchViewModel : ViewModel() {
    var db = FirebaseDatabase.getInstance()
    var users = db.getReference("users")

    private var _userData = MutableLiveData<List<User>>()
    var userData: LiveData<List<User>> = _userData
    init {
        viewModelScope.launch {
            fetchUser()
        }
    }

    suspend fun fetchUser() = withContext(Dispatchers.IO) {
        try {
            val snapshot = users.get().await() // Fetch threads asynchronously
            val resultList = mutableListOf<User>()

            for (threadSnapshot in snapshot.children) {
                val user = threadSnapshot.getValue(User::class.java)
                user?.let { user ->
                    resultList.add(user)
                }
            }
            val userList = resultList // Wait for all user fetches to complete
            _userData.postValue(userList) // Update LiveData on UI thread
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching threads: ${e.message}")
        }
    }
}


