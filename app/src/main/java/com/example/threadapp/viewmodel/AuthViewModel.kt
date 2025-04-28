package com.example.threadapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
                    _firebaseUser.value = auth.currentUser
                    getData(auth.currentUser!!.uid, context)
                } else {
                    println("yaha pr aya")
                    _error.value = "Something went wrong!!"
                }
            }
    }

    private fun getData(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var data = snapshot.getValue(User::class.java)
                    SharedPref.storeData(data!!.name,data!!.username,data!!.email,uid,data!!.imgUrl,context)
                    println("name" +SharedPref.getName(context))
                    println("image Url" +SharedPref.getImage(context))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun Register(
        email: String,
        password: String,
        name: String,
        username: String,
        imgUrl : String?,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                    saveDate(email, password, name, username, auth.currentUser?.uid,imgUrl, context)
                } else {
                    _error.value = "Something went wrong!!"
                }
            }
    }

    private fun saveDate(
        email: String,
        password: String,
        name: String,
        username: String,
        uid: String?,
        imgUrl:String?,
        context: Context
    ) {
        val userObject = User(name, username, email, password, uid,imgUrl)

        val db = Firebase.firestore
        val followers = db.collection("Followers").document(uid!!)
        val following = db.collection("Following").document(uid!!)

        followers.set(mapOf("followings_id" to listOf<String>()))
        following.set(mapOf("followers_id" to listOf<String>()))

        userRef.child(uid!!).setValue(userObject)
            .addOnSuccessListener {
                SharedPref.storeData(name, username, email, uid,imgUrl, context)
                println("name "+ SharedPref.getName(context))
                println("image Url "+ SharedPref.getImage(context))
            }
            .addOnFailureListener {

            }

    }

    fun logOut() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}