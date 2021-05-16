package com.example.reumanager.data.api

import com.example.reumanager.data.model.LoginApi
import com.example.reumanager.data.model.MyResponse
import com.example.reumanager.data.model.RegisterApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {

    @POST("auth")
    suspend fun auth(
        @Body userApi: LoginApi
    ): Response<MyResponse.MineUserInfo>

    @POST("register")
    suspend fun register(
        @Body registerApi: RegisterApi
    ): Response<MyResponse.MyResponseInfo>
}