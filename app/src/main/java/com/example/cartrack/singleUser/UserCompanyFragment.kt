package com.example.cartrack.singleUser

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
import com.example.cartrack.databinding.FragmentUserComapnyBinding
import com.example.cartrack.response.User
import com.example.cartrack.util.AppConstant
import com.example.cartrack.util.Status
import javax.inject.Inject

class UserCompanyFragment : Fragment() {
    private var key : Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var fragmentUserCompanyBinding: FragmentUserComapnyBinding
    private lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_comapny, container, false)

        val appComponent = (activity?.applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        val binding = FragmentUserComapnyBinding.bind(view)
        fragmentUserCompanyBinding = binding
        userDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(UserDetailsViewModel::class.java)

        binding.viewmodel = userDetailsViewModel
        binding.comapanyNameObservable = userDetailsViewModel.companyNameObservable
        binding.catchPhraseObservable = userDetailsViewModel.catchPhraseObservable
        binding.bsObservable = userDetailsViewModel.bSObservable

        progress = view.findViewById(R.id.progressBar)

        key = this.arguments?.getInt(AppConstant.KEY)


        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if ( activeNetwork != null && activeNetwork.isConnectedOrConnecting)setupObservers()

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
    private fun retrieveUser(user: User) {
        fragmentUserCompanyBinding.comapanyNameObservable!!.text = user.company.name
        fragmentUserCompanyBinding.catchPhraseObservable!!.text = user.company.catchPhrase
        fragmentUserCompanyBinding.bsObservable!!.text = user.company.bs
    }
}