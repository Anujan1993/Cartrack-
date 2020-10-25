package com.example.cartrack.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.SharedPref
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityLauncherBinding
import com.example.cartrack.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: SharedViewModel

    private var drawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var toolbar: Toolbar? = null
    private var navigationView: NavigationView? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var xyz: Switch? = null
    private lateinit var sharedpre: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)

        binding.viewmodel = sharedViewModel


        sharedpre = SharedPref(this)
        setDark()
       // super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer)
        val actionbar = supportActionBar
        actionbar!!.title = "List of Users"
        navigationView = findViewById(R.id.navigationView)
        navigationView!!.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.Open,
            R.string.close
        )
        actionBarDrawerToggle!!.drawerArrowDrawable.color = resources.getColor(R.color.secondaryLightColor)
        drawerLayout!!.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle!!.syncState()

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.replace(
            R.id.container_fragment,
            HomeFragment()
        )
        fragmentTransaction!!.commit()

        val navMenu = navigationView?.menu
        val menuItem = navMenu?.findItem(R.id.switch1)
        xyz = menuItem?.actionView as Switch
        if (sharedpre.loadNightModeState()){
            xyz!!.isChecked =true
        }
        xyz!!.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                sharedpre.setNightModeState(true)
                restartApp()
            }
            else{
                sharedpre.setNightModeState(false)
                restartApp()
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                fragmentManager = supportFragmentManager
                fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction!!.replace(
                    R.id.container_fragment,
                    HomeFragment()
                )
                fragmentTransaction!!.commit()
            }
            R.id.Log_out -> {
                sharedViewModel.setLogout()
                val intent = Intent(this,
                    LauncherActivity::class.java)
                startActivity(intent)
                finish();
            }
        }
        return false

    }
    fun restartApp(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

