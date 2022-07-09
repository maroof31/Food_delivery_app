package com.nevermindapp.newproject1.ui.rest

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.ui.auth.ProfileFragment
import com.nevermindapp.newproject1.ui.rest.fragments.Fragment_cart
import com.nevermindapp.newproject1.ui.rest.fragments.HomeFragment
import com.nevermindapp.newproject1.ui.rest.fragments.OrdersFragment
import com.razorpay.PaymentResultListener


class MainActivity : AppCompatActivity(), PaymentResultListener {
    var listener: PaymentListener? =null
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

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
        listener!!.onSuccess()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        listener!!.onFailure()
    }

    public fun getListener(listenerr: PaymentListener){
        this.listener=listenerr
    }

}