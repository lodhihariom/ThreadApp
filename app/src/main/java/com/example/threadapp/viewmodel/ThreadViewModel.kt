package com.example.threadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.threadapp.Model.ThreadData

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.database.FirebaseDatabase


class ThreadViewModel : ViewModel() {
    var db = FirebaseDatabase.getInstance()

    var threadRef = db.getReference("threads")

    private var _firebaseUser = MutableLiveData<FirebaseUser?>()
    var firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private var _isPosted = MutableLiveData<Boolean>()
    var isPosted: LiveData<Boolean> = _isPosted

    fun saveThread(
        uid: String?,
        imgUrl: String?="",
        thread:String,
        onSuccess:(Boolean)->Unit
    ) {
        val threadObject = ThreadData(uid,imgUrl,thread,System.currentTimeMillis().toString())
        threadRef.child(threadRef.push().key!!).setValue(threadObject)
            .addOnSuccessListener {
                onSuccess(true)
                _isPosted.value = true
            }
            .addOnFailureListener {
                _isPosted.value = false
            }
    }

}