package com.nevermindapp.newproject1.data.firebase

import Models.CatagoryModel
import Models.ProductsModel
import Models.UserModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val categories= MutableLiveData<List<CatagoryModel>>()
    private val products= MutableLiveData<List<ProductsModel>>()


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


}
