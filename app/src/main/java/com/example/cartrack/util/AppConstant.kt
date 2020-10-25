package com.example.cartrack.util

object AppConstant {
    const val USER_ID = "ID"
    const val LATITUDE = "lat"
    const val LONGITUDE = "lng"
    const val ADDRESS_NAME = "addressName"
    const val NAVIGATE_TO_REGISTER = "NavigateToRegister"
    const val REGISTER_SUCCESS ="Registration Success"
    const val KEY = "Key"
}
object ErrorObject{
    const val EMAIL_OBJECT = "Email"
    const val NAME_OBJECT = "Name"
    const val USERNAME_OBJECT = "Username"
    const val PHONE_OBJECT = "Phone"
    const val PASSWORD_OBJECT = "Password"
    const val CONFORM_PASSWORD_OBJECT = "Conform Password"
    const val PASSWORD_NOT_MATCH = "Password Not Matched"
}
object ErrorMessages{
    const val SERVER_ERROR = "Error Occurred!"
    const val EMAIL_ERROR = "Please Enter the Correct Email"
    const val PASSWORD_ERROR = "Please Enter the Correct Password"
    const val NAME_ERROR = "Please Enter the Name"
    const val PHONE_ERROR = "Please Enter the Phone Number"
    const val CONFORM_PASSWORD_ERROR = "Please Enter the Conform Password"
    const val PASSWORD_NOT_MATCHED = "Passwords are not matched"
    const val USER_NOT_EXIST = "User not exist!"
}
object EndPoints {
    const val GET_USERS = "users"
    const val GET_USER = "users/{id}"
    const val DATABASE_NAME = "cartrack.db"
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
}

object SharedPreferencesData{
    const val APP_USER ="APP_USER"
    const val LOGGED_IN = "LoggedIn"
    const val NIGHT_MODE = "Night Mode"
}