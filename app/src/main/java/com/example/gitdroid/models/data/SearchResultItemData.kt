package com.example.gitdroid.models.data

data class SearchResultItemData (
    val name: String = "",  // имя файла
    val path: String = "",  // путь к файлу
    val html_url: String = "",
    val repository: GHRepositoryData = GHRepositoryData(),
    val score: Float = 0.0F  // оценка результата поиска
)
