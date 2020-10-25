package com.example.cartrack.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.ui.MainActivity
import com.example.cartrack.register.RegisterActivity
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityLoginBinding
import com.example.cartrack.util.AppConstant
import com.example.cartrack.util.ErrorMessages
import com.example.cartrack.util.ErrorObject
import com.example.cartrack.util.Result
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewmodel = loginViewModel
        binding.emailObservable = loginViewModel.emailObservable
        binding.passwordObservable = loginViewModel.passwordObservable

        errorObserveViewModel(binding)
        observeViewModel()

        if (loginViewModel.loggedInOrNot()) navigateToHome()

    }

    private fun observeViewModel() {
        loginViewModel.result.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> navigateToHome()
                    is Result.Error ->Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
                    else -> {}
                }
            }
        })
    }

    private fun errorObserveViewModel(binding: ActivityLoginBinding) {
        loginViewModel.error.observe(this, Observer {
            it.let {
                when (it) {
                    ErrorObject.EMAIL_OBJECT -> {
                        binding.resetEmailMain.error = ErrorMessages.EMAIL_ERROR
                        binding.resetEmailMain.requestFocus()
                    }
                    ErrorObject.PASSWORD_OBJECT -> {
                        binding.LoginPasswordMain.error = ErrorMessages.PASSWORD_ERROR
                        binding.LoginPasswordMain.requestFocus()
                    }
                    AppConstant.NAVIGATE_TO_REGISTER -> navigateToRegister()
                    else -> {}
                }
            }
        })
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}