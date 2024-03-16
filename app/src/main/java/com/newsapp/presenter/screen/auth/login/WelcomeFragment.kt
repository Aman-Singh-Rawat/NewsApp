package com.newsapp.presenter.screen.auth.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.newsapp.R
import com.newsapp.databinding.FragmentWelcomeBinding
import java.util.concurrent.Executors

class WelcomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.cloud_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding = FragmentWelcomeBinding.inflate(
            inflater, container, false
        )
        binding.btnWelSignInWith.btnAllInOne.setOnClickListener {
            openSignInFragment()
        }
        binding.includeWelSignUp.tvSgnIn.setOnClickListener {
            openCreateAccountFragment()
        }
        binding.tvContinueWithGoogle.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
        changeText()
        return binding.root
    }

    private fun changeText() {
        binding.btnWelSignInWith.btnAllInOne.text = "Sign in with password"

    }

    private fun openCreateAccountFragment() {
        findNavController().navigate(R.id.createAccountFragment)
    }

    private fun openSignInFragment() {
        findNavController().navigate(R.id.signInFragment)
    }

    /* Google Authentication */
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            manageResult(task)
        }
    }
    private fun manageResult(task: Task<GoogleSignInAccount>) {
        Log.d("debugging", "manageResult")
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUi(account)
            }
        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Sign In Completed",
                    Toast.LENGTH_LONG).show()
                verifyUser()
            }
        }
        Log.d("debugging", "updateUi")
    }
    private fun verifyUser() {
        Log.d("debugging", "verifyUser")
        val user = Firebase.auth.currentUser
        user?.let {

            if (it.isEmailVerified) {
                findNavController().navigate(R.id.signInDialogFragment)
            } else {
                Toast.makeText(requireContext(), "Something wrong",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            verifyUser()
        }
    }

}