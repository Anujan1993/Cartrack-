package com.example.cartrack.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityLauncherBinding
import com.example.cartrack.login.LoginActivity
import com.example.cartrack.register.RegisterActivity
import javax.inject.Inject

class LauncherActivity : AppCompatActivity()   ,View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var register: Button
    private lateinit var login: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)

        binding.viewmodel = sharedViewModel


        register = findViewById(R.id.update)
        login = findViewById(R.id.SendEmail)

        register.setOnClickListener(this)
        login.setOnClickListener(this)
        if (sharedViewModel.loggedInOrNot())navigateToHome()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.update -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.SendEmail -> {
                val intent = Intent(this,
                    LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}