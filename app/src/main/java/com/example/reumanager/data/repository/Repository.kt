package com.example.reumanager.data.repository

import com.example.reumanager.data.api.RetrofitInstance
import com.example.reumanager.data.model.LoginApi
import com.example.reumanager.data.model.MyResponse
import com.example.reumanager.data.model.RegisterApi
import retrofit2.Response

class Repository {

    class Repository {

        suspend fun auth(userApi: LoginApi): Response<MyResponse.MineUserInfo> {
            return RetrofitInstance.api.auth(userApi)
        }

        suspend fun register(registerApi: RegisterApi): Response<MyResponse.MyResponseInfo> {
            return RetrofitInstance.api.register(registerApi)
        }
    }
}