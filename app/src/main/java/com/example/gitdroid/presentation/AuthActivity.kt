package com.example.gitdroid.presentation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.databinding.ActivityAuthBinding
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.presentation.vm.auth.AuthState
import com.example.gitdroid.presentation.vm.auth.AuthViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by viewModels {
        (application as GitDroidApplication).appComponent.authViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authBool = intent?.extras?.getBoolean("LOGOUT")
        Log.d(TAG, "Auth Bool from intent is: $authBool")
        if (authBool == true) {
            Log.d(TAG, "Before calling VM logout")
            authViewModel.logout()
        }
        Log.d(TAG, "Before setting up observer vm state is: ${authViewModel.authState.value}")
        setupObserver()
    }

    private fun setupObserver() {
        Log.d(TAG, "In setup observer")
        authViewModel.checkAuthorized()
        authViewModel.authState.observe(this) { authState ->
            when (authState) {
                AuthState.AUTHORIZED -> {
                    Log.d(TAG, "State is authorized. Before starting MainActivity")
                    Log.d(TAG, "Here vm state is: ${authViewModel.authState.value}")
                    // Go to hello screen
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                AuthState.UNAUTHORIZED -> {
                    Log.d(TAG, "State is UNauthorized. Before settingAuthClickListener")
                    Log.d(TAG, "Here vm state is: ${authViewModel.authState.value}")
                    setOnClickAuth()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent() called with: intent = $intent")
        if (intent?.extras?.getBoolean("LOGOUT") == true) {
            authViewModel.logout()
        }
    }

    private fun setOnClickAuth() {
        Log.d(TAG, "Setting onClickAuth")
        binding.authBtn.setOnClickListener {
            val email = binding.enterEmailEditText.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your github id", Toast.LENGTH_LONG)
                    .show()
            } else {
                Log.d(TAG, "Before calling VM for authorize")
                authViewModel.signInWithGithubProvider(email, object : AuthCallback {
                    override fun onRegister(a: FirebaseAuth, p: OAuthProvider): AuthResult {
                        Log.d(TAG, "In onRegister callback")

                        return Tasks.await(
                            a.startActivityForSignInWithProvider(this@AuthActivity, p))
                    }
                })
            }
        }
    }

    companion object {
        const val TAG = "AuthActLog"
    }
}