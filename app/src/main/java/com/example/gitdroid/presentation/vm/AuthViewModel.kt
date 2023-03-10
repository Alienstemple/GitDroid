package com.example.gitdroid.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.gitdroid.domain.ProjectsInteractor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel(/* // TODO private val authInteractor: AuthInteractor*/): ViewModel() {

    fun checkAuthorized(): Boolean {
        // TODO в репозиторий
        if (Firebase.auth.currentUser != null)
            return true
        return false
    }
}