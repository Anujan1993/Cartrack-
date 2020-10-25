package com.example.cartrack.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cartrack.util.loadLoginSharedPrefState
import com.example.cartrack.util.loadNightModeState
import com.example.cartrack.util.loginSharedPrefState
import com.example.cartrack.util.setNightModeState
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private var sharedPreferences: SharedPreferences
):ViewModel() {

    fun loggedInOrNot(): Boolean {
        return sharedPreferences.loadLoginSharedPrefState()
    }
    fun setLogout(){
        sharedPreferences.loginSharedPrefState(false)
    }
    fun setThemeMode(state : Boolean){
        sharedPreferences.setNightModeState(state)
    }
    fun lasdThemeMode(): Boolean {
       return sharedPreferences.loadNightModeState()
    }
}