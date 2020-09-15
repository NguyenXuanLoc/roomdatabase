package com.example.roomdatabase.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var users: MutableLiveData<List<UserModel>> = MutableLiveData()
}