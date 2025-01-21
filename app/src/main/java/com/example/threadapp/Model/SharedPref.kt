package com.example.threadapp.Model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.provider.ContactsContract.CommonDataKinds.Email

object SharedPref {
    fun storeData(name: String, userName: String, email: String, uid: String, context: Context) {
        var pref = context.getSharedPreferences("users", MODE_PRIVATE)
        var editor = pref.edit()
        editor.putString("name", name)
        editor.putString("userName", userName)
        editor.putString("email", email)
        editor.putString("uid", uid)
        editor.apply()
    }

    fun getName(context: Context): String {
        var pref = context.getSharedPreferences("users", MODE_PRIVATE)
        return pref.getString("name", "")!!
    }

    fun getEmail(context: Context): String {
        var pref = context.getSharedPreferences("users", MODE_PRIVATE)
        return pref.getString("email", "")!!
    }

    fun getUserName(context: Context): String {
        var pref = context.getSharedPreferences("users", MODE_PRIVATE)
        return pref.getString("userName", "")!!
    }
}