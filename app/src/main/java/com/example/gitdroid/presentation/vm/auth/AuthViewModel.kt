package com.example.gitdroid.presentation.vm.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthInteractor
import com.example.gitdroid.models.domain.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel авторизации. Хранит статус авторизации [AuthState] в LiveData
 */
class AuthViewModel(private val authInteractor: AuthInteractor) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()

    /**
     * Статус авторизации
     */
    val authState: LiveData<AuthState> = _authState

    /**
     * Проверка, авторизован ли пользователь и обновление статуса авторизации
     */
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

    /**
     * Авторизация
     */
    fun signIn(email: String, authCallbackInstance: AuthCallback) {
        viewModelScope.launch(Dispatchers.IO) {
            authInteractor.signIn(email, authCallbackInstance)
            _authState.postValue(AuthState.AUTHORIZED)
        }
    }

    /**
     * Логаут
     */
    fun logout() {
        authInteractor.logout()
        _authState.value = AuthState.UNAUTHORIZED
    }

    companion object {
        const val TAG = "AuthVmLog"
    }
}