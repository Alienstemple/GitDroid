package com.example.gitdroid.presentation.vm.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.models.domain.AuthState
import com.example.gitdroid.models.domain.Project
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel(private val authInteractor: AuthInteractor) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        _authState.postValue(
            when(checkAuthorized()) {
                true -> {AuthState.AUTHORIZED}
                false -> {AuthState.UNAUTHORIZED}
            }
        )
    }

    private fun checkAuthorized(): Boolean = authInteractor.checkAuthorized()

    fun signInWithGithubProvider(email: String) {
        authInteractor.signInWithGithubProvider(email)
        Log.d(TAG, "Sign In successfully, ${AuthState.AUTHORIZED}")
        _authState.value = AuthState.AUTHORIZED
    }

    fun logout() {
        authInteractor.logout()
        Log.d(TAG, "Logout successfully, ${AuthState.UNAUTHORIZED}")
        _authState.value = AuthState.UNAUTHORIZED
    }

    companion object {
        const val TAG = "AuthVmLog"
    }
}