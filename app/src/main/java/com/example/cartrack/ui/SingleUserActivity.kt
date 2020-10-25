package com.example.cartrack.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.singleUser.MapViewActivity
import com.example.cartrack.singleUser.UserCompanyFragment
import com.example.cartrack.singleUser.UserDetailsFragment
import com.example.cartrack.util.AppConstant
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class SingleUserActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private var iD : Int = 0
    private val args= Bundle()
    private lateinit var lat:String
    private lateinit var lng:String
    private lateinit var addressName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_single_user)


        sharedViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)
        setDarkAction()


        var actionbar = supportActionBar
        actionbar?.title = "User Profile"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        /*** Get data from Adapter  */
        val intent = intent
        iD = intent.getIntExtra(AppConstant.USER_ID, 0)
        lat = intent.getStringExtra(AppConstant.LATITUDE).toString()
        lng = intent.getStringExtra(AppConstant.LONGITUDE).toString()
        addressName = intent.getStringExtra(AppConstant.ADDRESS_NAME).toString()

        var navigationView = findViewById<View>(R.id.BottomNavigation) as BottomNavigationView
        navigationView.bringToFront()

        val fragment = UserDetailsFragment()
        args.putInt(AppConstant.KEY, iD)
        fragment.arguments = args
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.add(R.id.container_fragment, fragment)
        fragmentTransaction!!.commit()


        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Personal -> {
                    val fragment1 =
                        UserDetailsFragment()
                    fragment1.arguments = args
                    actionbar?.title = "User Profile"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment1)
                    fragmentTransaction!!.commit()
                }
                R.id.Loaction -> {
                    val intent = Intent(this, MapViewActivity::class.java)
                    intent.putExtra(AppConstant.LATITUDE, lat)
                    intent.putExtra(AppConstant.LONGITUDE, lng)
                    intent.putExtra(AppConstant.ADDRESS_NAME, addressName)
                    startActivity(intent)
                }
                R.id.company -> {
                    val fragment3 =
                        UserCompanyFragment()
                    fragment3.arguments = args
                    actionbar?.title = "User Company"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment3)
                    fragmentTransaction!!.commit()
                }
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun setDarkAction() {
        if (sharedViewModel.lasdThemeMode()){
            setTheme(R.style.AppThemeDarkActionBar)
        }
        else{
            setTheme(R.style.AppThemeLiteActionBar)
        }
    }
}