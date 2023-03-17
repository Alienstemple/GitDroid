package com.example.gitdroid.models.domain

/**
 * Состояние запроса к GitHub API (выполняется, выполнен, ошибка)
 */
enum class SearchState {
    LOADING, COMPLETED, ERROR
}