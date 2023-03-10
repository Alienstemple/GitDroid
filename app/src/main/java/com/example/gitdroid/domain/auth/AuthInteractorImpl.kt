package com.example.gitdroid.domain.auth

class AuthInteractorImpl(private val authRepository: AuthRepository) : AuthInteractor {
    override fun checkAuthorized() = authRepository.checkAuthorized()

    override fun signInWithGithubProvider(email: String) {
        authRepository.signInWithGithubProvider(email)
    }

    override fun logout() {
        authRepository.logout()
    }
}