package com.nevermindapp.newproject1.ui.rest.fragments

import adapters.CartItemsAdapters
import adapters.CatagoryAdapter
import adapters.ClickListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentCartBinding
import com.nevermindapp.newproject1.ui.ProductDetailViewModel
import database.AppDatabase
import database.CartModel
import net.simplifiedcoding.ui.auth.ProductDetailViewModelFactory


class Fragment_cart : Fragment(), ClickListener {
    private lateinit var factory: ProductDetailViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel
    private var binding: FragmentCartBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cart, container, false)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding!!.root


        val firebase= FirebaseSource()
        val repository= UserRepository(firebase)
        val db=AppDatabase.getDatabase(requireContext())
        factory=ProductDetailViewModelFactory(repository,db)
        viewModel= ViewModelProviders.of(this,factory).get(ProductDetailViewModel::class.java)


        var layoutManager = LinearLayoutManager(requireContext())
        binding!!.rvcartview.layoutManager=layoutManager
        val adapter = CartItemsAdapters(requireContext(),this)
        binding!!.rvcartview.adapter=adapter



        viewModel.products.observe(viewLifecycleOwner, {
            Log.e("cartSize","${it.size}  + 00")
            adapter.updateList(it)
        })


        return view
    }

    override fun deleteproduct(item: CartModel) {
        viewModel.deleteProduct(item)
    }

    override fun minusClick(item: CartModel) {
        viewModel.decreaseqtyundb(item)
    }

    override fun plusClick(item: CartModel) {
        viewModel.increaseqtyindb(item)
    }


}