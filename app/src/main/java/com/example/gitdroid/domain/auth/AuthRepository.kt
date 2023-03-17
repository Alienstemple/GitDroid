package com.example.gitdroid.domain.auth

/**
 * Репозиторий авторизации
 */
interface AuthRepository {
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
    suspend fun signIn(email: String, authCallbackInstance: AuthCallback)

    /**
     * Выполняет логаут
     */
    fun logout()
}