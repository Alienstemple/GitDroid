package com.example.gitdroid.domain.auth

interface AuthInteractor {
    fun checkAuthorized(): Boolean
    suspend fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback)
    fun logout()
}