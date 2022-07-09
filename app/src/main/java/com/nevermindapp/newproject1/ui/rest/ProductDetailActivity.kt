package com.nevermindapp.newproject1.ui.rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.ActivityProductDetailBinding
import com.nevermindapp.newproject1.databinding.ActivityProductsBinding
import com.nevermindapp.newproject1.ui.ProductDetailListener
import com.nevermindapp.newproject1.ui.ProductDetailViewModel
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import database.AppDatabase
import net.simplifiedcoding.ui.auth.MainViewModelFactory
import net.simplifiedcoding.ui.auth.ProductDetailViewModelFactory

class ProductDetailActivity : AppCompatActivity(), ProductDetailListener {
    private lateinit var factory: ProductDetailViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel
    lateinit var binding: ActivityProductDetailBinding
    lateinit var productTitle: String
    lateinit var productprice: String
    lateinit var id: String
    lateinit var link: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val firebase = FirebaseSource()
        val repository = UserRepository(firebase)
        val db = AppDatabase.getDatabase(this)
        factory = ProductDetailViewModelFactory(repository, db)
        viewModel = ViewModelProviders.of(this, factory).get(ProductDetailViewModel::class.java)


        viewModel.Listener = this

        link = intent.getStringExtra("Link").toString()
        productprice = intent.getStringExtra("ProductPrice").toString()
        productTitle = intent.getStringExtra("ProductTitle").toString()
        id = intent.getStringExtra("ProductId").toString()

        viewModel.price = Integer.parseInt(productprice)
        binding.minusCardBtn.setOnClickListener {
            viewModel.decreaseqty()
        }

        binding.plusCardBtn.setOnClickListener {
            viewModel.increaseqty()
        }

        binding.addToCartBtn.setOnClickListener {
            viewModel.addTocart(id, productTitle, link)
            finish()
        }



        binding.tvproductpricedes.text = "$  ${viewModel.price}"
        binding.numberItemTxt.text = viewModel.qty.toString()
        if (viewModel.qty == 1) {
            binding.totalPriceTxt.text = "$  ${viewModel.price}"
        } else {
            binding.totalPriceTxt.text = "$ ${viewModel.totalPrice}"
        }
        binding.tvproducttitdes.text = productTitle
        Glide.with(this).load(link).into(binding.foodPic)
    }

    override fun showprice(qty: Int, total_price: Int) {
        binding.numberItemTxt.text = qty.toString()
        binding.totalPriceTxt.text = total_price.toString()
    }
}