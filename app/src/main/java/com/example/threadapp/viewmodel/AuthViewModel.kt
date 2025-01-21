package com.example.threadapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.User
import com.example.threadapp.Model.post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class AuthViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()
    var userRef = db.getReference("users")
    private var _firebaseUser = MutableLiveData<FirebaseUser?>()
    var firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private var _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
//                    _firebaseUser.value = auth.currentUser
                    _firebaseUser.postValue(auth.currentUser)
                    getData(auth.currentUser!!.uid, context)
                } else {
                    _error.value = "Something went wrong!!"
                }
            }
    }

    private fun getData(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var user = snapshot.getValue(User::class.java)
                    SharedPref.storeData(user!!.name, user.username, user.email, uid, context)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _error.value = error.message
            }

        })

    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        username: String,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveDate(email, password, name, username, auth.currentUser?.uid, context)
                } else {
                    _error.value = "Something went wrong!!"
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }

    private fun saveDate(
        email: String,
        password: String,
        name: String,
        username: String,
        uid: String?,
        context: Context
    ) {
        val userObject = User(name, username, email, password, uid)

        userRef.child(uid!!).setValue(userObject)
            .addOnSuccessListener {
                SharedPref.storeData(name, username, email, uid, context)
                println("Data saved successfully")
                println("name " + SharedPref.getName(context))
            }
            .addOnFailureListener {
                _error.value = "Failed to save data:${it.message}"
                println("Failed to save data:${it.message}")

            }

    }

    private val _posts = MutableLiveData<List<post>>()
    val posts: LiveData<List<post>> = _posts

    fun fetchPosts(uid: String) {
        val postsRef = db.getReference("posts").child(uid)
        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postsList = mutableListOf<post>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(post::class.java)
                    if (post != null) {
                        postsList.add(post)
                    }
                }
                _posts.value = postsList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }


}