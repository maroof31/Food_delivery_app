package com.nevermindapp.newproject1.data.repository

import Models.CatagoryModel
import Models.ProductsModel
import Models.UserModel
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import database.AppDatabase
import database.CartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository( private val firebase: FirebaseSource) {
    private var categories_rep= MutableLiveData<List<CatagoryModel>>()
    private var products_rep= MutableLiveData<List<ProductsModel>>()


    fun login(email: String, password: String): Task<AuthResult> {
      return firebase.login(email, password)
    }

    fun register(email: String, password: String): Task<AuthResult> {
       return firebase.register(email, password)
    }

    fun currentUser() = firebase.currentUser()

    fun logout() {
       return  firebase.logout()
    }

    fun getCategories(): MutableLiveData<List<CatagoryModel>>{
        categories_rep=firebase.getCategoryList()
        return categories_rep
    }

    fun getProducts(CategoryId:String):MutableLiveData<List<ProductsModel>>{
        products_rep=firebase.getProductList(CategoryId)
        return products_rep
    }



    fun addToCart(db:AppDatabase,item:CartModel){
        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext  db.CArtDao().insertAll(item)
            }
        }
    }

    fun deleteitem(db:AppDatabase,cartitem: CartModel) {
        GlobalScope.launch(Dispatchers.Main){
            db.CArtDao().deteleTask(cartitem.UId)
        }
    }

    fun decreaseqtydb(db:AppDatabase,item: CartModel) {
        var quat=item.quantity
        quat=quat-1
        GlobalScope.launch(Dispatchers.Main) {
            db.CArtDao().updatequantity(quat,item.UId)
        }
    }

    fun increaseqtydb(db:AppDatabase,item: CartModel) {
        var quat=item.quantity
        quat=quat+1
        GlobalScope.launch(Dispatchers.Main) {
            db.CArtDao().updatequantity(quat,item.UId)
        }
    }

    fun createUser(finaldata:UserModel): Task<Void> {
       return firebase.createUser(finaldata)
    }



}