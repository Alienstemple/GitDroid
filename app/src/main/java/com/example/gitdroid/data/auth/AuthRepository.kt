package com.example.gitdroid.data.auth

import android.util.Log
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthRepository {
    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        TokenStorage.idToken = tokens.idToken

        Log.d(TAG, "Access token = ${TokenStorage.accessToken}")
        Log.d(TAG, "Refresh token = ${TokenStorage.accessToken}")
        Log.d(TAG, "Id token = ${TokenStorage.accessToken}")
    }

    companion object {
        const val TAG = "AuthRepoLog"
    }
}