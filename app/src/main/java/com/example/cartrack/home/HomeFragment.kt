@file:Suppress("DEPRECATION")

package com.example.cartrack.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.adapter.UserAdapter
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.FragmentHomeBinding
import com.example.cartrack.response.User
import com.example.cartrack.ui.SingleUserActivity
import com.example.cartrack.util.AppConstant
import com.example.cartrack.util.Status
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var adapter: UserAdapter
    private lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_home, container,
            false
        )

        val appComponent = (activity?.applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        val binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.viewmodel = homeViewModel

        recyclerView = view.findViewById(R.id.ListOfUser)
        progress = view.findViewById(R.id.progressBar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) setupObservers()

        return view
    }


    private fun setupObservers() {
        activity?.let {
            homeViewModel.getUsers().observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerView.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            resource.data?.let { users -> retrieveList(users) }
                        }
                        Status.ERROR -> {
                            recyclerView.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            progress.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun retrieveList(users: List<User>) {
        UserAdapter(users, onUserItemSelected).apply {
            recyclerView.adapter = this
        }
    }

    private val onUserItemSelected = object : UserAdapter.Callback {
        override fun onItemClicked(user: User) {
            with(user) {
                val id = id
                val lat = address.geo.lat
                val lng = address.geo.lng
                val addressName = address.street

                startActivity(Intent(requireContext(), SingleUserActivity::class.java).apply {
                    putExtra(AppConstant.USER_ID, id)
                    putExtra(AppConstant.LATITUDE, lat)
                    putExtra(AppConstant.LONGITUDE, lng)
                    putExtra(AppConstant.ADDRESS_NAME, addressName)
                })
            }
        }
    }
}



