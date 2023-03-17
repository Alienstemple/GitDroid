package com.example.gitdroid.presentation.vm.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.presentation.vm.search.SearchResultViewModel
import javax.inject.Inject

/**
 * Фабрика для создания [AuthViewModel]
 */
class AuthViewModelFactory @Inject constructor(private val authInteractor: AuthInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthInteractor::class.java)
            .newInstance(authInteractor)
    }
}