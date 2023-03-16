package com.example.gitdroid.domain.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

/**
 * Интерфейс, метод [onRegister] необходим для запуска Firebase-авторизации с контекстом Activity
 * и полечения результата авторизации
 */
interface AuthCallback {
    /**
     * Запуск Firebase-авторизации с контекстом Activity
     * и полечение результата авторизации
     * @param a [FirebaseAuth]
     * @param p [OAuthProvider]
     * @return [AuthResult]
     */
    fun onRegister(a: FirebaseAuth, p: OAuthProvider): AuthResult
}