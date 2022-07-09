package com.nevermindapp.newproject1.ui.rest

import Models.CatagoryModel
import Models.ProductsModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import com.zerowasteapp.zerowaste.adapters.OrderModel

class MainViewModel(private val repository: UserRepository, private val utils: UserSessionUtils) :
    ViewModel() {

     private var _categories = MutableLiveData<List<CatagoryModel>>()
     val categories: LiveData<List<CatagoryModel>>
        get() = _categories

     private var _products = MutableLiveData<List<ProductsModel>>()
     val products: LiveData<List<ProductsModel>>
        get() = _products



    fun getCategories() {
        _categories = repository.getCategories()
    }

    fun getProducts(categoryId: String) {
        _products = repository.getProducts(categoryId)
    }

}