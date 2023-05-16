package com.azwar.androidtestcao.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azwar.androidtestcao.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class UserViewModel: ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers(context: Context) {
        try {
            val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
            val userListType = object : TypeToken<List<User>>() {}.type
            val users = Gson().fromJson<List<User>>(json, userListType)
            _users.value = users
        } catch (e: IOException) {
            // Handle exception here
        }
    }
}