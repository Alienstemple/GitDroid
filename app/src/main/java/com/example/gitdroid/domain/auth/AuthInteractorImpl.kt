package com.example.gitdroid.domain.auth

/**
 * Имплементирует интерфейс [AuthInteractor]
 * Отвечает за взаимодействие с репозиторием авторизации: проверка авторизации, логин, логаут
 */
class AuthInteractorImpl(private val authRepository: AuthRepository) : AuthInteractor {
    override fun checkAuthorized() = authRepository.checkAuthorized()

    override suspend fun signIn(
        email: String,
        authCallbackInstance: AuthCallback,
    ) {
        authRepository.signIn(email, authCallbackInstance)
    }

    override fun logout() {
        authRepository.logout()
    }
}