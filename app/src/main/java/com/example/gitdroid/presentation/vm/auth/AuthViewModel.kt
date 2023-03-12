package com.example.gitdroid.presentation.vm.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.presentation.vm.search.SearchResultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authInteractor: AuthInteractor) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        _authState.postValue(
            when (checkAuthorized()) {
                true -> {
                    AuthState.AUTHORIZED
                }
                false -> {
                    AuthState.UNAUTHORIZED
                }
            }
        )
    }

    private fun checkAuthorized(): Boolean {
        Log.d(TAG, "Checking authorized")
        return authInteractor.checkAuthorized()
    }

    fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback) {
        Log.d(TAG,
            "signInWithGithubProvider() called with: email = $email")
        viewModelScope.launch(Dispatchers.IO) {
            authInteractor.signInWithGithubProvider(email, authCallbackInstance)
            Log.d(TAG, "Sign In successfully, ${AuthState.AUTHORIZED}")
            _authState.postValue(AuthState.AUTHORIZED)
        }
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