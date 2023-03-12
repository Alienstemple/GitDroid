package com.example.gitdroid.domain.auth

interface AuthRepository {
    fun checkAuthorized(): Boolean
    suspend fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback)
    fun logout()
}