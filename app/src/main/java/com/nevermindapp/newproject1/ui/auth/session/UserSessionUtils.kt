package com.nevermindapp.newproject1.ui.auth.session

import android.content.Context
import android.content.SharedPreferences
import com.nevermindapp.newproject1.utils.Constants


class UserSessionUtils(private val context: Context) {



    fun saveUserToken(token:String){
        var sharedpreferences: SharedPreferences =context.getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =sharedpreferences.edit()
        editor.putString("user_token",token)
        editor.apply()
        editor.commit()
    }



    fun saveUserName(name:String){
        var sharedpreferences: SharedPreferences =context.getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =sharedpreferences.edit()
        editor.putString("user_name",name)
        editor.apply()
        editor.commit()
    }



    fun  logoutUser(){
        var sharedpreferences: SharedPreferences =context.getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =sharedpreferences.edit()
        editor.clear().commit()
    }



    fun getUserName(): String? {
        var sharedpreferences: SharedPreferences =context.getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE)
          val name=  sharedpreferences.getString("user_name","")
        return name
    }


}