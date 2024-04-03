package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class ViewModelProfile(private val application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
    private val firestore by lazy { Firebase.firestore }

    fun updateUserProfile(fullName: String, userName: String, bio: String, website: String) {
        val currentUser = prefs.getUser()
        if (currentUser != null) {
            val user = currentUser.copy(fullName = fullName, userName = userName, bio = bio, website = website)
            prefs.saveUser(user)
            firestore.collection(DatabaseCollection.users).document(user.uid).set(user)
        }
    }
    fun updateUserProfile(imageUri: Uri) {
        /*var currentUser = prefs.getUser()
        if (currentUser != null) {
            val user = currentUser.copy(profile = imageUri)
        }
        prefs.saveUser(user)
        firestore.collection(DatabaseCollection.users).document()*/
    }
    fun updateUserProfile(imageUri: Uri, onSuccess: () -> Unit) {
        val storageRef = storageRef.child(System.currentTimeMillis().toString())
        val uploadTask = storageRef.putFile(imageUri!!)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                val currentUser = prefs.getUser()
                if (currentUser != null) {
                    val user = currentUser.copy(profile = downloadUri)
                    prefs.saveUser(user)
                    onSuccess()
                }

            } else {
                //callback(null)
            }
        }
    }


}
