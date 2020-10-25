package com.example.cartrack.singleUser

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.FragmentUserDetailsBinding
import com.example.cartrack.response.User
import com.example.cartrack.util.AppConstant
import com.example.cartrack.util.Status
import javax.inject.Inject

class UserDetailsFragment : Fragment() {
    private var key : Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var fragmentUserDetailsBinding: FragmentUserDetailsBinding
    private lateinit var progress: ProgressBar

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_details, container, false)
        val appComponent = (activity?.applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        val binding = FragmentUserDetailsBinding.bind(view)
        fragmentUserDetailsBinding = binding
        userDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(UserDetailsViewModel::class.java)

        progress = view.findViewById(R.id.progressBar)

        binding.viewmodel = userDetailsViewModel
        binding.nameObservable = userDetailsViewModel.nameObservable
        binding.usernameObservable = userDetailsViewModel.usernameObservable
        binding.emailObservable = userDetailsViewModel.emailObservable
        binding.phoneObservable = userDetailsViewModel.phoneObservable
        binding.websiteObservable = userDetailsViewModel.websiteObservable
        binding.streetObservable = userDetailsViewModel.streetObservable
        binding.suiteObservable = userDetailsViewModel.suiteObservable
        binding.cityObservable = userDetailsViewModel.cityObservable
        binding.zipcodeObservable= userDetailsViewModel.zipObservable



        key = this.arguments?.getInt(AppConstant.KEY)


        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) setupObservers()

        return view

    }
    private fun setupObservers() {
        activity?.let {
            userDetailsViewModel.getSingleUsers(key!!).observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            progress.visibility = View.GONE
                            resource.data?.let {
                                    user -> retrieveUser(user)
                            }
                        }
                        Status.ERROR -> {
                            progress.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            progress.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }
    private fun retrieveUser(user:User) {
        fragmentUserDetailsBinding.nameObservable?.text = user.name
        fragmentUserDetailsBinding.usernameObservable?.text = user.username
        fragmentUserDetailsBinding.emailObservable?.text = user.email
        fragmentUserDetailsBinding.phoneObservable?.text = user.phone
        fragmentUserDetailsBinding.websiteObservable?.text = user.website
        fragmentUserDetailsBinding.streetObservable?.text = user.address.street
        fragmentUserDetailsBinding.suiteObservable?.text =user.address.suite
        fragmentUserDetailsBinding.cityObservable?.text = user.address.city
        fragmentUserDetailsBinding.zipcodeObservable?.text = user.address.zipcode
    }
}