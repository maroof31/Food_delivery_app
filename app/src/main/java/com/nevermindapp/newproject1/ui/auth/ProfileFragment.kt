package com.nevermindapp.newproject1.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentHomeBinding
import com.nevermindapp.newproject1.databinding.FragmentProfileBinding
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import com.nevermindapp.newproject1.ui.rest.MainViewModel
import net.simplifiedcoding.ui.auth.AuthViewModelFactory
import net.simplifiedcoding.ui.auth.MainViewModelFactory


class ProfileFragment : Fragment() {
    private lateinit var factory: AuthViewModelFactory
    private lateinit var viewModel: AuthViewModel

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding!!.root


        val util: UserSessionUtils = UserSessionUtils(requireContext())
        val firebase= FirebaseSource()
        val repository= UserRepository(firebase)
        factory=AuthViewModelFactory(repository,util)
        viewModel= ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)


        binding!!.userName.text=util.getUserName()
        binding!!.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            val intent=Intent(requireActivity(),LoginActivity::class.java)
            startActivity(intent)
        }
        return view
    }



}