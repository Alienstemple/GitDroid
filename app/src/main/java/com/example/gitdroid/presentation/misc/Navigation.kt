package com.example.gitdroid.presentation.misc

import androidx.fragment.app.Fragment

fun Fragment.navigation(): Navigation {
    return requireActivity() as Navigation
}

/**
 * Интерфейс, реализующий навигацию между фрагментами
 */
interface Navigation {
    /**
     * Открытие фрагмента со списком всех проектов
     */
    fun openProjects()
    /**
     * Открытие фрагмента поиска GitHub
     */
    fun openCodeSearch()
    /**
     * Открытие стартового фрагмента с меню
     */
    fun openHello()

    /**
     * Логаут
     */
    fun logout()

    /**
     * Открытие фрагмента настроек
     */
    fun openSettings()
}