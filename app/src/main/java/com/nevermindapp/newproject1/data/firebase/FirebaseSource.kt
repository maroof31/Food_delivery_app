package com.nevermindapp.newproject1.data.firebase

import Models.CatagoryModel
import Models.ProductsModel
import Models.UserModel
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zerowasteapp.zerowaste.adapters.AdminOrderModel
import com.zerowasteapp.zerowaste.adapters.OrderModel

class FirebaseSource {
   var uid: String=""
    var user_name1: String=""
   var  user_id1:String=""
   var order_date1: String=""
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val categories= MutableLiveData<List<CatagoryModel>>()
    private val products= MutableLiveData<List<ProductsModel>>()
    private val order_history=MutableLiveData<List<OrderModel>>()


    fun getCategoryList() : MutableLiveData<List<CatagoryModel>> {
        firebaseDb.collection("appdata").document("CatagoriesDoc").collection("CatagoriesAdded")
            .addSnapshotListener { value, error ->
                val data = value?.toObjects(CatagoryModel::class.java)
                categories.value=data
                for (i in data!!) {
                    Log.d("TAGCAT", i.name + " ")
                }
            }
        return categories
    }

    fun getProductList(categoryId:String): MutableLiveData<List<ProductsModel>> {
        firebaseDb.collection("appdata").document("CatagoriesDoc").
        collection("CatagoriesAdded").document(categoryId).
        collection("products")
            .addSnapshotListener { value, error ->
                val data = value?.toObjects(ProductsModel::class.java)
                products.value=data
            }
        return products
    }




        fun login(email: String, password: String): Task<AuthResult> {
            return firebaseAuth.signInWithEmailAndPassword(email, password)
        }

        fun register(email: String, password: String): Task<AuthResult> {
            return firebaseAuth.createUserWithEmailAndPassword(email, password)
        }

        fun logout() {
            firebaseAuth.signOut()
        }

        fun currentUser() = firebaseAuth.currentUser
      fun createUser(finaldata: UserModel): Task<Void> {
      return firebaseDb.collection("Users").document(finaldata.id).set(finaldata)
    }

    fun placeNewOrder(
        user_id: String,
       user_name: String,
        order_date: String,
        mo_number: String,
        address: String,
        zipcode: String,
        orderDetails: String,
        order_total: Int,
        accpeted: Boolean,
        delivered: Boolean,
        payment: String
    ): Task<Void> {

        val UID = firebaseDb.collection("Users").document(user_id).collection("Myorders")
            .document().id
        val finaldata = OrderModel(UID,user_id,user_name
            ,order_date,mo_number, address, zipcode,orderDetails,order_total.toString(),accpeted,delivered,payment)
         uid=UID
         user_id1=user_id
        order_date1=order_date
        user_name1=user_name
      return  firebaseDb.collection("Users").document(user_id)
            .collection("Myorders").document(UID).set(finaldata)

    }






    fun saveordertoAdmindb(
    ): Task<Void> {

        val admin_order_id = firebaseDb.collection("allOrders").document().id

        val admin_order_data= AdminOrderModel(user_name1,user_id1,uid,order_date1)

       return firebaseDb.collection("allOrders").document(admin_order_id).set(admin_order_data)
    }

    fun getOrders(uid: String): MutableLiveData<List<OrderModel>> {
        firebaseDb.collection("Users").document(uid).
        collection("Myorders")
            .addSnapshotListener { value, error ->
                val data = value?.toObjects(OrderModel::class.java)
                order_history.value=data
            }
        return order_history
    }


}
