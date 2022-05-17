package com.nevermindapp.newproject1.ui.rest

import adapters.ProductsAdapter
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.ActivityProductsBinding
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import net.simplifiedcoding.ui.auth.MainViewModelFactory

class ProductsActivity : AppCompatActivity() {
    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityProductsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val categoryId = intent.getStringExtra("categoryId").toString()
        val catname = intent.getStringExtra("categoryName").toString()
        binding.tvcategory.text = catname

        val util: UserSessionUtils = UserSessionUtils(this)
        val firebase = FirebaseSource()
        val repository = UserRepository(firebase)
        factory = MainViewModelFactory(repository, util)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)


        if (this!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding!!.rvproducts.layoutManager = (GridLayoutManager(this, 2))
        } else {
            binding!!.rvproducts.layoutManager = (GridLayoutManager(this, 3))
        }

        val adapter = ProductsAdapter(this)
        binding!!.rvproducts.adapter = adapter

        viewModel.getProducts(categoryId)
        viewModel.products.observe(this, Observer {
            Log.e("catlistsize", "${it.size}  + 00")
            adapter.updateList(it)
        })
    }
}