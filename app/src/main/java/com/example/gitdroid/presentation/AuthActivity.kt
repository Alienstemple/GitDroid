package com.example.gitdroid.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.data.SessionManager
import com.example.gitdroid.databinding.ActivityAuthBinding
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.domain.AuthRepositoryImpl
import com.example.gitdroid.presentation.vm.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by viewModels {
        (application as GitDroidApplication).appComponent.authViewModelFactory()
    }

    fun AuthRepositoryImpl.startActivityForSignInWithProvider(activity: FragmentActivity) {
    // TODO extension method
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (authViewModel.checkAuthorized()) {
            // Go to hello screen
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // set onClick for auth button
            setOnClickAuth()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent() called with: intent = $intent")
        if (intent?.extras?.getBoolean("LOGOUT") == true) {
            FirebaseAuth.getInstance().signOut()  // TODO вынести отсюда!!!
            SessionManager(this).removeAuthToken()
            Log.d(MainActivity.TAG, "Logout success")
        }
    }

    private fun setOnClickAuth() {
        binding.authBtn.setOnClickListener {
            val email = binding.enterEmailEditText.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your github id", Toast.LENGTH_LONG)
                    .show()
            } else {
                authViewModel.signInWithGithubProvider(email)
            }
        }
    }

    companion object {
        const val TAG = "AuthActLog"
    }
}