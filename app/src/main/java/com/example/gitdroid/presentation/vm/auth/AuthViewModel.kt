package com.example.gitdroid.presentation.vm.auth

import androidx.lifecycle.ViewModel
import com.example.gitdroid.domain.auth.AuthInteractor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel(private val authInteractor: AuthInteractor): ViewModel() {

    fun checkAuthorized(): Boolean {
        // TODO в интерактор
        if (Firebase.auth.currentUser != null)
            return true
        return false
    }

    fun signInWithGithubProvider(email: String) {
        // TODO в интерактор
    }

    fun logout() {

    }
}