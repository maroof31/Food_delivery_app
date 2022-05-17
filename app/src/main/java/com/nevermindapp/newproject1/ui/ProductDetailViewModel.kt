package com.nevermindapp.newproject1.ui

import Models.CatagoryModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.auth.AuthListener
import database.AppDatabase
import database.CartModel

class ProductDetailViewModel(private val repository: UserRepository, private val db: AppDatabase) :
    ViewModel() {

    var products: LiveData<List<CartModel>>

    init {
        products = db.CArtDao().fetchAll()
    }

    var Listener: ProductDetailListener? = null
    var price: Int = 0
    var qty: Int = 1
    var totalPrice: Int = 0


    fun increaseqty() {
        qty = qty + 1
        totalPrice = price * qty
        Listener!!.showprice(qty, totalPrice)
    }

    fun decreaseqty() {
        qty = qty - 1
        Listener!!.showprice(qty, totalPrice)
    }

    fun addTocart(id: String, title: String, link: String) {
        repository.addToCart(db, CartModel(null, id, title, price.toString(), link, qty))
    }

    fun deleteProduct(cartitem: CartModel) {
        repository.deleteitem(db, cartitem)
    }

    fun decreaseqtyundb(item: CartModel) {
        repository.decreaseqtydb(db, item)
    }

    fun increaseqtyindb(item: CartModel) {
        repository.increaseqtydb(db, item)
    }


}