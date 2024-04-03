package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article? = null
    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
    var imageUri: Uri? = null
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun getArticle(): Article? {
        return currentArticle
    }

    fun addArticle(imagePath: String, title: String, story: String) {
        currentArticle = Article(image = imagePath, title = title, story = story)
    }

    fun publishArticle(topic: String, tags: List<String>) {
        currentArticle = currentArticle?.copy(topic = topic, tags = tags)

        //TODO upload all data to firebase store
        //TODO use articles collection

        prefs.getUser()?.let { user ->
            currentArticle?.let { article ->
                val articleMap = gson.fromJson(gson.toJson(article), HashMap::class.java)

                val articlesRef = firestore.collection(DatabaseCollection.articles)

                articlesRef.add(articleMap)
                    .addOnSuccessListener { documentReference ->
                        val docId = documentReference.id
                    }
                    .addOnFailureListener { e ->

                    }
            }
        }
    }

    fun uploadImageToFirebase(onSuccess: () -> Unit) {
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
                currentArticle = currentArticle?.copy(image = downloadUri, time = System.currentTimeMillis())
                onSuccess()
            } else {
                //callback(null)
            }
        }
    }
    fun getArticleData(onSuccess: (ArrayList<Article>) -> Unit) {
        val articleList = ArrayList<Article>()

        firestore.collection(DatabaseCollection.articles).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for(data in it.documents) {
                        val article: Article? = data.toObject(Article::class.java)
                        if(article != null) {
                            articleList.add(article)
                        }
                    }
                    onSuccess(articleList)
                }
            }
            .addOnFailureListener {
            }
    }


    fun clearArticleData() {
        currentArticle = null
    }
}