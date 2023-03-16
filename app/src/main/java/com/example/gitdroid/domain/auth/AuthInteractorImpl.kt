package com.example.gitdroid.domain.auth

class AuthInteractorImpl(private val authRepository: AuthRepository) : AuthInteractor {
    override fun checkAuthorized() = authRepository.checkAuthorized()

    override suspend fun signInWithGithubProvider(
        email: String,
        authCallbackInstance: AuthCallback,
    ) {
        authRepository.signInWithGithubProvider(email, authCallbackInstance)
    }

    override fun logout() {
        authRepository.logout()
    }
}