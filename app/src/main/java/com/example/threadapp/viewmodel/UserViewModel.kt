package com.example.threadapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.threadapp.Model.User
import com.example.threadapp.Model.ThreadData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    var db = FirebaseDatabase.getInstance()
    var threads = db.getReference("threads")
    var users = db.getReference("users")

    private var _threadData = MutableLiveData<List<ThreadData>>()
    var threadData: LiveData<List<ThreadData>> = _threadData

    private var _userData = MutableLiveData<User>()
    var userData: LiveData<User> = _userData

    private var _followerList = MutableLiveData<List<String>>()
    var followerList: LiveData<List<String>> = _followerList

    private var _followingList = MutableLiveData<List<String>>()
    var followingList: LiveData<List<String>> = _followingList

    fun fetchUser(userId:String){
        users.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(User::class.java)
                data?.let {
                    _userData.postValue(data!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    suspend fun fetchThread(uid:String) = withContext(Dispatchers.IO) {
        println("yaha tk bhi nhi aa paa rhew")
        try {
            val resultList = mutableListOf<ThreadData>()
            threads.orderByChild("uid").equalTo(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    println("snapshot "+ snapshot)
                    for (threadSnapShot in snapshot.children){
                        var thread = threadSnapShot.getValue(ThreadData::class.java)
                        resultList.add(thread!!)
                    }
                    val threadList = resultList // Wait for all user fetches to complete
                    _threadData.postValue(threadList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            // Update LiveData on UI thread
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching threads: ${e.message}")
        }
    }

    val firestore = Firebase.firestore
    fun followUser(user_id :String, current_id :String){
        val followerRef = firestore.collection("Followers").document(user_id)
        val followingRef = firestore.collection("Followings").document(current_id)

        followerRef.update("followers_id", FieldValue.arrayUnion(current_id))
        followingRef.update("following_id",FieldValue.arrayUnion(user_id))
    }

    fun getFollowers(user_id:String){
        firestore.collection("Followers").document(user_id)
            .addSnapshotListener { value, error ->
                var list = value?.get("followers_id") as?List<String>?: listOf()
                _followerList.postValue(list)
            }
    }

    fun getFollowing(user_id:String){
        firestore.collection("Followings").document(user_id)
            .addSnapshotListener { value, error ->
                var list = value?.get("following_id") as?List<String>?: listOf()
                _followingList.postValue(list)
            }
    }

}