package com.example.cartrack.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.ui.LauncherActivity
import com.example.cartrack.ui.MainActivity
import com.example.cartrack.ui.SharedViewModel
import javax.inject.Inject

class InitActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        sharedViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)

        if (sharedViewModel.loggedInOrNot()) restartApp() else longToLogin()

    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun longToLogin() {
        val intent = Intent(this, LauncherActivity::class.java)
        startActivity(intent)
    }

}