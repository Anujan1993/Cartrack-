package com.example.cartrack.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityRegisterBinding
import com.example.cartrack.login.LoginActivity
import com.example.cartrack.ui.MainActivity
import com.example.cartrack.util.AppConstant
import com.example.cartrack.util.ErrorMessages
import com.example.cartrack.util.ErrorObject
import com.example.cartrack.util.Result
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var country: String
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {

        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner = findViewById(R.id.Up_Post_AddressMain)

        ArrayAdapter.createFromResource(
            this,
            R.array.country_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner!!.adapter = adapter
        }

        registerViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewmodel = registerViewModel
        binding.emailObservable = registerViewModel.emailObservable
        binding.passwordObservable = registerViewModel.passwordObservable
        binding.nameObservable = registerViewModel.nameObservable
        binding.phoneObservable = registerViewModel.phoneObservable
        binding.conformPasswordObservable = registerViewModel.conformPasswordObservable

        errorObserveViewModel(binding)
        spinner.onItemSelectedListener = this

        observeViewModel()

        if (registerViewModel.loggedInOrNot()) navigateToHome()

    }

    private fun observeViewModel() {
        registerViewModel.result.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> navigateToLogin(it)

                    is Result.Error -> {
                        Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    Result.Loading -> TODO()
                }
            }
        })
    }

    private fun errorObserveViewModel(binding: ActivityRegisterBinding) {
        registerViewModel.error.observe(this, Observer {
            it.let {
                when (it) {
                    ErrorObject.EMAIL_OBJECT-> {
                        binding.UpEmailMain.error = ErrorMessages.EMAIL_ERROR
                        binding.UpEmailMain.requestFocus()
                    }
                    ErrorObject.NAME_OBJECT -> {
                        binding.UpUname.error = ErrorMessages.NAME_ERROR
                        binding.UpUname.requestFocus()
                    }
                    ErrorObject.PHONE_OBJECT -> {
                        binding.UpPhoneNumberMain.error = ErrorMessages.PHONE_ERROR
                        binding.UpPhoneNumberMain.requestFocus()
                    }
                    ErrorObject.CONFORM_PASSWORD_OBJECT -> {
                        binding.UpConfomPasswordMain.error = ErrorMessages.CONFORM_PASSWORD_ERROR
                        binding.UpConfomPasswordMain.requestFocus()
                    }
                    ErrorObject.PASSWORD_OBJECT -> {
                        binding.UpPasswordMain.error = ErrorMessages.PASSWORD_ERROR
                        binding.UpPasswordMain.requestFocus()
                    }
                    else -> {
                        binding.UpConfomPasswordMain.error = ErrorMessages.PASSWORD_NOT_MATCHED
                        binding.UpConfomPasswordMain.requestFocus()
                    }
                }
            }
        })
    }

    private fun navigateToLogin(it: Result<String>) {
        Toast.makeText(this, AppConstant.REGISTER_SUCCESS, Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        // An item was selected. You can retrieve the selected item using
        country = parent?.getItemAtPosition(pos).toString()
        registerViewModel.getCountry(country!!)
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

