package com.example.gitdroid.models.domain

/**
 * Элемент поискового ответа
 */
data class SearchResultItem(
    val fileName: String,  // имя файла
    val filePath: String,  // путь к файлу
    val htmlFileUrl: String,
    val ghRepository: GHRepository,
    val searchResScore: Float,  // оценка результата поиска
)
