package com.nevermindapp.newproject1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.auth.User
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.zerowasteapp.zerowaste.adapters.OrderModel
import database.AppDatabase
import database.CartModel
import java.text.SimpleDateFormat
import java.util.*


class ProductDetailViewModel(private val repository: UserRepository, private val db: AppDatabase) : ViewModel() {


    private val user_name: MutableLiveData<String?>? = null

    fun getUserName(): MutableLiveData<String?>? {
        return user_name
    }


    var products: LiveData<List<CartModel>>
    private var _orders = MutableLiveData<List<OrderModel>>()
    val orders: LiveData<List<OrderModel>>
        get() = _orders

    fun getOrders(){
        _orders=repository.getOrders()
    }


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


    fun currentDate(): String {
        val timeStamp = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().time)
        Log.e("date",timeStamp)
        val timess=System.currentTimeMillis()
        return timess.toString()
    }

    fun placeOrder(
        name: String,
        mobile: String,
        address: String,
        pincode: String,
        orderDetails: String,
        totalAmount: Int,
        isAccpeted: Boolean,
        isDelivered: Boolean,
        payment: String
    ): Task<Void> {
       var user_id=repository.currentUser()!!.uid
        var date=currentDate()
       return repository.placeOrder(date,user_id,name,mobile,address,pincode,orderDetails,totalAmount,isAccpeted,isDelivered,payment)
    }

    fun savetoAdmin(): Task<Void> {
       return repository.savetoAdminDB()
    }


    fun logoutUser(){
       // utils.logoutUser()
        repository.logout()
    }


}