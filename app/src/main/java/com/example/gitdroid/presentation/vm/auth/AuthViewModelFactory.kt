package com.example.gitdroid.presentation.vm.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.auth.AuthInteractor
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(private val authInteractor: AuthInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthInteractor::class.java)
            .newInstance(authInteractor)
    }
}