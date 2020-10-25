package com.example.cartrack.repository

import com.example.cartrack.api.ApiInterface
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getUsers() = apiInterface.getPostJson()
    suspend fun getSingleUsers(userID:Int) = apiInterface.singleUser(userID)

}