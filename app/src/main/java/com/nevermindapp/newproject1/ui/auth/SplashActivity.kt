package com.nevermindapp.newproject1.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.ActivitySplashBinding
import com.nevermindapp.newproject1.ui.rest.MainActivity
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import net.simplifiedcoding.ui.auth.AuthViewModelFactory
import android.net.ConnectivityManager
import android.view.View


class SplashActivity : AppCompatActivity(), AuthListener {
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseSource = FirebaseSource()
        val repository = UserRepository(firebaseSource)
        val util = UserSessionUtils(this)
        val factory = AuthViewModelFactory(repository, util)
        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener = this



        if (!isNetworkConnected()) {
            binding.noconnectionview.visibility = View.VISIBLE
        } else {
            binding.noconnectionview.visibility = View.INVISIBLE
            val handler = Handler()
            handler.postDelayed({
                //Write whatever to want to do after delay specified (1 sec)
                Log.d("Handler", "Running Handler")
                if (viewModel.getCurrentUser() == null) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else if (viewModel.getCurrentUser() != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }, 2000)
        }
    }


    override fun onStarted() {

    }

    override fun onSuccess() {

    }


    override fun onFailure(message: String) {

    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}