package com.example.gitdroid.models.domain

/**
 * Пользователь GitHub
 * @property ghLogin Логин
 * @property userId ID
 */
data class User(
    val ghLogin: String,
    val userId: Int,
)
