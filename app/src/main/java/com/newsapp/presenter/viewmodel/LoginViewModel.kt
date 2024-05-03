package com.newsapp.presenter.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.R
import com.newsapp.data.models.User
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.PrefKeys
import com.newsapp.util.SharedPrefsManager

class LoginViewModel(private val application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    prefs.putBoolean(PrefKeys.IS_LOGGED_IN, true)
                    it.result.user?.let { user ->
                        firestore.collection(DatabaseCollection.USERS).document(user.uid).get()
                            .addOnCompleteListener {
                                if (it.result.data != null) {
                                    val json = gson.toJson(it.result.data)
                                    prefs.saveUser(gson.fromJson(json, User::class.java))
                                } else {
                                    prefs.saveUser(User(uid = user.uid, email = user.email?:""))
                                }
                                onSuccess.invoke()
                            }
                    }
                } else {
                    onError(it.exception?.message ?: "Something went wrong..")
                }
            }
        } else {
            onError("Please enter valid email & password.")
        }
    }

    fun getGoogleSignInClient(): GoogleSignInClient? {
        return try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.cloud_client_id)).requestEmail()
                .build()
            GoogleSignIn.getClient(application.applicationContext, gso)
        } catch (e: Exception) {
            null
        }
    }

    fun authenticateGoogleLogin(
        credential: AuthCredential,
        onSuccessSignIn: () -> Unit,
        onSuccessSignup: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signOut()
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                prefs.putBoolean(PrefKeys.IS_LOGGED_IN, true)
                it.result.user?.let { user ->
                    firestore.collection(DatabaseCollection.USERS).document(user.uid).get()
                        .addOnCompleteListener {
                            if (it.result.data != null) {
                                val json = gson.toJson(it.result.data)
                                val userData = gson.fromJson(json, User::class.java)
                                prefs.saveUser(userData)
                                if(userData.userName.isNotEmpty()){
                                    onSuccessSignIn.invoke()
                                }else{
                                    onSuccessSignup.invoke()
                                }
                            } else {
                                prefs.saveUser(User(uid = user.uid, email = user.email?:""))
                                firestore.collection(DatabaseCollection.USERS).document(user.uid).set(
                                    User(uid = user.uid, email = user.email?:"")
                                )
                                onSuccessSignup.invoke()
                            }
                        }
                }

            } else {
                onError(it.exception?.message ?: "Something went wrong..")
            }
        }
    }

    fun logout() {
        prefs.putBoolean(PrefKeys.IS_LOGGED_IN, false)
        auth.signOut()
        getGoogleSignInClient()?.signOut()
    }
    fun resetPassword(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onError.invoke(it.message.toString())
            }
    }
}