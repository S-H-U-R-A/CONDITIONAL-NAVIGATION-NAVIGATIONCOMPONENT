package com.example.navegaciontest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var _user: MutableLiveData<Boolean> = MutableLiveData(false)
    val user: LiveData<Boolean> = _user

    var result: MutableLiveData<Boolean> = MutableLiveData()

    fun login( user: String, pass: String  ): LiveData<Boolean>{

        result.value = false

        if(user == "Sergio" && pass == "123"){
            result.value = true
            _user.value = true
        }

        return result

    }

}