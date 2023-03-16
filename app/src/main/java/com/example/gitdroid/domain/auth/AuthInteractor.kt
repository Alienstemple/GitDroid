package com.example.gitdroid.domain.auth

/**
 * Интерфейс для взаимодействия с репозиторием авторизации: проверка авторизации, логин, логаут
 */
interface AuthInteractor {
    /**
     * Проверяет, авторизован ли пользователь
     * @return [Boolean]
     */
    fun checkAuthorized(): Boolean

    /**
     * Выполняет авторизацию
     * @param email Email пользователя
     * @param authCallbackInstance Коллбэк для запуска авторизации с контекстом Activity
     */
    suspend fun signInWithGithubProvider(email: String, authCallbackInstance: AuthCallback)

    /**
     * Выполняет логаут
     */
    fun logout()
}