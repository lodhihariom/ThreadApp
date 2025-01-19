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

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class AuthViewModel:ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var db  = FirebaseDatabase.getInstance()
    var userRef = db.getReference("users")
    private var _firebaseUser = MutableLiveData<FirebaseUser?>()
    var firebaseUser : LiveData<FirebaseUser?> = _firebaseUser

    private var _error = MutableLiveData<String>()
    var error : LiveData<String> = _error

    fun login(email:String , password : String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
//                    _firebaseUser.value = auth.currentUser
                    _firebaseUser.postValue(auth.currentUser)
                    getData(auth.currentUser!!.uid,context )
                }else{
                    _error.value = "Something went wrong!!"
                }
            }
    }

    private fun getData(uid: String, context:Context) {
         userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                 if ( snapshot.exists()){
                        var user = snapshot.getValue(User::class.java)
                        SharedPref.storeData(user!!.name,user.username,user.email,uid,context)
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
        ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.value = "Something went wrong!!"
                }
            }
    }
    fun signOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }

}