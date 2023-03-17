package com.example.gitdroid.models.domain

/**
 * Элемент поискового ответа
 * @property fileName Имя файла
 * @property filePath Путь к файлу в дереве каталогов GitHub-репозитория
 * @property htmlFileUrl Ссылка на файл
 * @property ghRepository Репозиторий, в котором хранится файл
 * @property searchResScore Релевантность ответа
 */
data class SearchResultItem(
    val fileName: String,  // имя файла
    val filePath: String,  // путь к файлу
    val htmlFileUrl: String,
    val ghRepository: GHRepository,
    val searchResScore: Float,  // оценка результата поиска
)
