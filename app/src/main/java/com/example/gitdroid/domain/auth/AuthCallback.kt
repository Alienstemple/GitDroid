package com.example.gitdroid.domain.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

interface AuthCallback {
    fun onRegister(a: FirebaseAuth, p: OAuthProvider): AuthResult
}