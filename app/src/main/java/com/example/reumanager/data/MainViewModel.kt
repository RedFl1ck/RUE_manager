package com.example.reumanager.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reumanager.data.model.LoginApi
import com.example.reumanager.data.model.MyResponse
import com.example.reumanager.data.model.RegisterApi
import com.example.reumanager.data.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository.Repository): ViewModel() {

    val myResponse : MutableLiveData<Response<MyResponse.MineUserInfo>> = MutableLiveData()
    val myRegResponse : MutableLiveData<Response<MyResponse.MyResponseInfo>> = MutableLiveData()

    fun auth(userApi: LoginApi){
        viewModelScope.launch {
            val response = repository.auth(userApi)
            myResponse.value = response
        }
    }

    fun register(registerApi: RegisterApi){
        viewModelScope.launch {
            val response = repository.register(registerApi)
            myRegResponse.value = response
        }
    }
}