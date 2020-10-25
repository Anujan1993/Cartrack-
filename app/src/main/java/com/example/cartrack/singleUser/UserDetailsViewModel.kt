package com.example.cartrack.singleUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cartrack.repository.NetworkUserRepository
import com.example.cartrack.util.ErrorMessages
import com.example.cartrack.util.Resource
import com.example.cartrack.util.TextObservable
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private  val networkUserRepository: NetworkUserRepository):ViewModel() {

    fun getSingleUsers(userID: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkUserRepository.getSingleUsers(userID)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: ErrorMessages.SERVER_ERROR))
        }
    }
    val nameObservable = TextObservable()
    val usernameObservable = TextObservable()
    val emailObservable = TextObservable()
    val phoneObservable = TextObservable()
    val websiteObservable = TextObservable()
    val streetObservable = TextObservable()
    val suiteObservable = TextObservable()
    val cityObservable = TextObservable()
    val zipObservable = TextObservable()
    val companyNameObservable = TextObservable()
    val catchPhraseObservable = TextObservable()
    val bSObservable = TextObservable()
}