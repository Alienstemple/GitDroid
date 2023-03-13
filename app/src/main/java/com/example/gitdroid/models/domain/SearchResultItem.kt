package com.example.gitdroid.models.domain

data class SearchResultItem (
    val fileName: String = "",  // имя файла
    val filePath: String = "",  // путь к файлу
    val htmlFileUrl: String = "",
    val ghRepository: GHRepository = GHRepository(),
    val searchResScore: Float = 0.0F  // оценка результата поиска
)
