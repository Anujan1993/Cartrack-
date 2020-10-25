package com.example.cartrack.util

import android.content.SharedPreferences

fun SharedPreferences.loadNightModeState(): Boolean {
    return getBoolean(SharedPreferencesData.NIGHT_MODE, false)
}
fun SharedPreferences.setNightModeState(state: Boolean) {
    edit()
    .putBoolean(SharedPreferencesData.NIGHT_MODE, state).apply()
}

fun SharedPreferences.loadLoginSharedPrefState(): Boolean {
    return getBoolean(SharedPreferencesData.LOGGED_IN, false)
}

fun SharedPreferences.loginSharedPrefState(state: Boolean) {
    edit()
        .putBoolean(SharedPreferencesData.LOGGED_IN, state)
        .apply()
}
