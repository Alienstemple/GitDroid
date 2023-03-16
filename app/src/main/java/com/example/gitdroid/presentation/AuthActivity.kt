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
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authBool = intent?.extras?.getBoolean("LOGOUT")
        if (authBool == true) {
            authViewModel.logout()
        }
        setupObserver()
    }

    private fun setupObserver() {
        authViewModel.checkAuthorized()
        authViewModel.authState.observe(this) { authState ->
            when (authState) {
                AuthState.AUTHORIZED -> {
                    // Go to hello screen
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                AuthState.UNAUTHORIZED -> {
                    setOnClickAuth()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.extras?.getBoolean("LOGOUT") == true) {
            authViewModel.logout()
        }
    }

    private fun setOnClickAuth() {
        binding.authBtn.setOnClickListener {
            val email = binding.enterEmailEditText.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your github id", Toast.LENGTH_LONG)
                    .show()
            } else {
                Log.d(TAG, "Before calling VM for authorize")
                authViewModel.signInWithGithubProvider(email, object : AuthCallback {
                    override fun onRegister(a: FirebaseAuth, p: OAuthProvider): AuthResult {

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