package com.nevermindapp.newproject1.ui.rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.ui.auth.ProfileFragment
import com.nevermindapp.newproject1.ui.rest.fragments.Fragment_cart
import com.nevermindapp.newproject1.ui.rest.fragments.HomeFragment
import com.nevermindapp.newproject1.ui.rest.fragments.OrdersFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentcontainer, HomeFragment())
        transaction.commit()
        bottomNavigation()
    }

    private fun bottomNavigation() {
        val homeBtn = findViewById<LinearLayout>(R.id.homeBtn)
        val cartBtn = findViewById<LinearLayout>(R.id.cartBtn)
        val profilebtn = findViewById<LinearLayout>(R.id.profilebtn)
        val ordersbtn = findViewById<LinearLayout>(R.id.ordersbtn)
        homeBtn.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, HomeFragment())
            transaction.commit()
        }
        cartBtn.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, Fragment_cart())
            transaction.commit()
        }
        profilebtn.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, ProfileFragment())
            transaction.commit()
        }

        ordersbtn.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, OrdersFragment())
            transaction.commit()
        }

    }

}