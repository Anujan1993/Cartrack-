package com.example.cartrack.di

import com.example.cartrack.app.InitActivity
import com.example.cartrack.ui.MainActivity
import com.example.cartrack.home.HomeFragment
import com.example.cartrack.login.LoginActivity
import com.example.cartrack.register.RegisterActivity
import com.example.cartrack.singleUser.UserCompanyFragment
import com.example.cartrack.singleUser.UserDetailsFragment
import com.example.cartrack.ui.BaseActivity
import com.example.cartrack.ui.LauncherActivity
import com.example.cartrack.ui.SingleUserActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,  NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(userDetailsFragment: UserDetailsFragment)
    fun inject(userCompanyFragment: UserCompanyFragment)
    fun inject(launcherActivity: LauncherActivity)
    fun inject(initActivity: InitActivity)
    fun inject(baseActivity: BaseActivity)
    fun inject(singleUserActivity: SingleUserActivity)
}