package com.example.gitdroid.domain.auth
import com.google.firebase.auth.*

interface AuthCallback {
    fun onRegister(a : FirebaseAuth, p : OAuthProvider) : AuthResult
}