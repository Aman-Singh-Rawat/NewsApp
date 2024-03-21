package com.newsapp.ui.profileNav.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newsapp.util.PrefKeys.BIO
import com.newsapp.util.PrefKeys.FULL_NAME
import com.newsapp.util.PrefKeys.USER_NAME
import com.newsapp.util.PrefKeys.WEBSITE
import com.newsapp.util.SharedPrefsManager

class ViewModelProfile(private val application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    fun setData(fullName: String, userName: String, bio: String, website: String) {
        prefs.putString(FULL_NAME, fullName)
        prefs.putString(USER_NAME, userName)
        prefs.putString(BIO, bio)
        prefs.putString(WEBSITE, website)
    }
}