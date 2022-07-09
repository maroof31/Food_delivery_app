package com.nevermindapp.newproject1.ui.auth

import Models.UserModel
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils

class AuthViewModel(private val repository: UserRepository,private val utils: UserSessionUtils): ViewModel() {
    private val TAG="AVIEWMODEL";

    //email and password for the input
    var email: String? = null
    var password: String? =null

    var name:String?=null
    var emailsignUp:String?=null
    var passwordSinup:String?=null
    var confirmPassword:String?=null
    var address:String?=null
    
    //auth listener
    var authListener: AuthListener? = null




    fun getCurrentUser(): FirebaseUser? {
        return repository.currentUser()
    }





    fun login(){
            authListener!!.onStarted()
            val auth:FirebaseAuth= FirebaseAuth.getInstance()
            //validating email and password
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Log.e(TAG,email+""+password)
                authListener?.onFailure("Invalid email or password")
                return
            }else{
              val authresponse=repository.login(email!!, password!!)
                authresponse.addOnCompleteListener {
                    if (it.isSuccessful) {
                        authListener!!.onSuccess()
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("loginsuccess", "signInWithEmail:success")
                        val user = auth.currentUser
                        Log.e(TAG,user.toString()+ auth.currentUser?.uid)
                        utils.saveUserToken(user.toString())
                    } else {
                        // If sign in fails, display a message to the user.
                         authListener!!.onFailure("failedlogin signInWithEmail:failure ${it.exception}")
                    }
                }
                Log.e(TAG,authresponse.toString())
            }
        }


    fun goToSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun signUp(){
        authListener!!.onStarted()
        if (emailsignUp.isNullOrEmpty() || passwordSinup.isNullOrEmpty() || confirmPassword.isNullOrEmpty()){
            authListener!!.onFailure("enter valid email or password")
            return
        }
        if (confirmPassword!=passwordSinup){
            authListener!!.onFailure("Passwords do not match")
            return
        }
        if (passwordSinup!!.length<6){
            authListener!!.onFailure("Password should be more than 6 chars")
        }
         val ans= repository.register(emailsignUp!!,passwordSinup!!)
          ans.addOnCompleteListener {
              Log.e(TAG,it.toString())
              val auth:FirebaseAuth= FirebaseAuth.getInstance()

              if (it.isSuccessful){
                  utils.saveUserToken(auth.currentUser?.uid.toString())
                  val user = auth.currentUser
                  val id=auth.currentUser!!.uid
                  utils.saveUserToken(user.toString())
                  SaveUserData(id)
              }else{
                  authListener!!.onFailure(it.exception.toString())
              }
          }

    }


    fun SaveUserData(id:String){
         utils.saveUserName(name!!)
        lateinit var finaldata:UserModel
        if (address?.length!=0){
            finaldata = UserModel(id,name!!, emailsignUp!!, passwordSinup!!,address!!,)
        }else{
            finaldata = UserModel(id,name!!, emailsignUp!!, passwordSinup!!,"nil")
        }
        repository.createUser(finaldata).addOnSuccessListener {
            authListener!!.onSuccess()
        authListener!!.onFailure("Signed up successfully")

       // saveCredentials()

      }
        .addOnFailureListener { e ->
            authListener!!.onFailure("failed signing up try again")
        }
    }

    fun logoutUser(){
        utils.logoutUser()
        repository.logout()
    }

}