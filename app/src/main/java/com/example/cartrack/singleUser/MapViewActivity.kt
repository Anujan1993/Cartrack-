package com.example.cartrack.singleUser

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.ui.BaseActivity
import com.example.cartrack.ui.SharedViewModel
import com.example.cartrack.util.AppConstant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var lat: String
    private lateinit var lng: String
    private lateinit var addressName: String
    override fun onCreate(savedInstanceState: Bundle?) {

        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)


        sharedViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)

        setDarkAction()

        val actionbar = supportActionBar
        actionbar!!.title = "User Location"
        actionbar.setDisplayHomeAsUpEnabled(true)


        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(
                this,
                "Add your own API key in MapWithMarker/app/secure.properties as MAPS_API_KEY=YOUR_API_KEY",
                Toast.LENGTH_LONG
            ).show()
        }

        val intent = intent
        lat = intent.getStringExtra(AppConstant.LATITUDE).toString()
        lng = intent.getStringExtra(AppConstant.LONGITUDE).toString()
        addressName = intent.getStringExtra(AppConstant.ADDRESS_NAME).toString()


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.apply {
            val sydney = LatLng(lat!!.toDouble(), lng!!.toDouble())
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title(addressName)
            )
            moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}