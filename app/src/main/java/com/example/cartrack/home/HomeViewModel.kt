package com.example.cartrack.home

import androidx.lifecycle.*
import com.example.cartrack.repository.NetworkUserRepository
import com.example.cartrack.util.ErrorMessages
import com.example.cartrack.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val networkUserRepository: NetworkUserRepository
) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkUserRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: ErrorMessages.SERVER_ERROR))
        }
    }

}