package com.example.cartrack.api

import com.example.cartrack.response.User
import com.example.cartrack.util.EndPoints
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {

    @GET(EndPoints.GET_USERS)
    suspend fun getPostJson(): List<User>

    @GET(EndPoints.GET_USER)
    suspend fun singleUser(@Path("id") id: Int): User
}