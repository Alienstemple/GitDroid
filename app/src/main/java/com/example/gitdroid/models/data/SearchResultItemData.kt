package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

data class SearchResultItemData (
    @SerializedName("name") val name: String = "",  // имя файла
    @SerializedName("path") val path: String = "",  // путь к файлу
    @SerializedName("html_url") val htmlUrl: String = "",
    @SerializedName("repository") val repository: GHRepositoryData = GHRepositoryData(),
    @SerializedName("score") val score: Float = 0.0F  // оценка результата поиска
)
