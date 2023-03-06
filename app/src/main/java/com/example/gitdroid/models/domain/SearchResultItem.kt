package com.example.gitdroid.models.domain

data class SearchResultItem (
    val name: String = "",  // имя файла
    val path: String = "",  // путь к файлу
    val html_url: String = "",
    val repository: GHRepository = GHRepository(),
    val score: Float = 0.0F  // оценка результата поиска
)
