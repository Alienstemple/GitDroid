package com.example.gitdroid.models.domain

/**
 * Состояние загрузки списка проектов
 * (загружается, загружен, ошибка)
 */
enum class ProjectLoadState {
    LOADING, COMPLETED, ERROR
}