package com.example.gitdroid.presentation.vm.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authInteractor: AuthInteractor) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun checkAuthorized() {
        _authState.postValue(
            when (authInteractor.checkAuthorized()) {
                true -> {
                    AuthState.AUTHORIZED
                }
                false -> {
                    AuthState.UNAUTHORIZED
                }
            }
        )
    }

    fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback) {
        viewModelScope.launch(Dispatchers.IO) {
            authInteractor.signInWithGithubProvider(email, authCallbackInstance)
            _authState.postValue(AuthState.AUTHORIZED)
        }
    }

    fun logout() {
        authInteractor.logout()
        _authState.value = AuthState.UNAUTHORIZED
    }

    companion object {
        const val TAG = "AuthVmLog"
    }
}