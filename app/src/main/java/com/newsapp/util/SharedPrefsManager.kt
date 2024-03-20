package com.newsapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.newsapp.models.User


class SharedPrefsManager private constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val gson:Gson = Gson()

    companion object {
        private const val SHARED_PREFS_NAME = "news_app_prefs"
        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPrefsManager? = null

        @Synchronized
        fun getInstance(context: Context): SharedPrefsManager {
            if (instance == null) {
                instance = SharedPrefsManager(context)
            }
            return instance!!
        }
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getUser(): User? {
        return try {
            val json = getString(PrefKeys.USER,"")
            gson.fromJson(json,User::class.java)
        }catch (e:Exception){
            null
        }
    }

    fun saveUser(user: User) {
        putString(PrefKeys.USER,gson.toJson(user))
    }
    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

object PrefKeys{
    const val IS_LOGGED_IN = "is_logged_in"
    const val USER = "logged_in_user"
    const val IS_LOGGED_OUT = "user_is_loggedout"

}


