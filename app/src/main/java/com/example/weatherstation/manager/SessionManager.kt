package com.example.weatherstation.manager

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.weatherstation.R
import java.util.Date

object SessionManager {
    const val USER_TOKEN = "user_token"

    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    private fun saveString(context: Context, key: String, value: String){
        val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    private fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(this.USER_TOKEN, null)
    }

    fun clearData(context: Context) {
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.commit()
    }

    fun isTokenExpired(token: String): Boolean {
        return try {
            val jwt = JWT(token)
            val expiresAt: Date? = jwt.expiresAt
            expiresAt?.before(Date()) ?: true
        } catch (e: Exception) {
            true
        }
    }
}