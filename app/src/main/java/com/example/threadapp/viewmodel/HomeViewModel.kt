package com.example.threadapp.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.threadapp.Model.User
import com.example.threadapp.Model.ThreadData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@SuppressLint("SuspiciousIndentation")
class HomeViewModel:ViewModel() {
    var db = FirebaseDatabase.getInstance()
    var threadRef = db.getReference("threads")
    var users = db.getReference("users")

    private var _threadAndUser = MutableLiveData<List<Pair<ThreadData,User>>>()
    var threadAndUser: LiveData<List<Pair<ThreadData,User>>> = _threadAndUser
    init {
        viewModelScope.launch {
            fetchThreadAndUser()
        }
    }

    suspend fun fetchThreadAndUser() = withContext(Dispatchers.IO) {
        try {
            val snapshot = threadRef.get().await() // Fetch threads asynchronously
            val resultList = mutableListOf<Deferred<Pair<ThreadData, User>>>()

            for (threadSnapshot in snapshot.children) {
                val thread = threadSnapshot.getValue(ThreadData::class.java)
                thread?.let { threadData ->
                    val deferredPair = async {
                        val user = fetchUser(threadData.uid!!) // Fetch user concurrently
                        threadData to user
                    }
                    resultList.add(deferredPair)
                }
            }

            val threadUserList = resultList.awaitAll() // Wait for all user fetches to complete
            _threadAndUser.postValue(threadUserList) // Update LiveData on UI thread
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching threads: ${e.message}")
        }
    }

    private suspend fun fetchUser(uid: String): User = withContext(Dispatchers.IO) {
        try {
            val snapshot = users.child(uid).get().await()
            snapshot.getValue(User::class.java) ?: User() // Return user or default
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching user: ${e.message}")
            User() // Return a default user if an error occurs
        }
    }
}


