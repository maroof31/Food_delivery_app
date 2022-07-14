package com.nevermindapp.newproject1.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.ActivityLoginBinding
import com.nevermindapp.newproject1.ui.rest.MainActivity
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import net.simplifiedcoding.ui.auth.AuthViewModelFactory

class LoginActivity : AppCompatActivity(),AuthListener {
    lateinit var binding:ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val firebaseSource= FirebaseSource()
        val repository=UserRepository(firebaseSource)
        val util:UserSessionUtils= UserSessionUtils(this)
        val factory=AuthViewModelFactory(repository,utils =util)

         binding= DataBindingUtil.setContentView(this, R.layout.activity_login)

         viewModel=ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener=this

    }

    override fun onStarted() {
        binding.spinKit1.visibility= View.VISIBLE
    }

    override fun onSuccess() {
        binding.spinKit1.visibility= View.INVISIBLE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onFailure(message: String) {
        binding.spinKit1.visibility= View.INVISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}