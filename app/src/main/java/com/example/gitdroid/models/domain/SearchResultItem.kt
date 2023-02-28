package com.example.gitdroid.models.domain

data class SearchResultItem (
    val name: String,  // имя файла
    val path: String,  // путь к файлу
    val html_url: String,
    val repository: GHRepository,
    val score: Float  // оценка результата поиска
)
