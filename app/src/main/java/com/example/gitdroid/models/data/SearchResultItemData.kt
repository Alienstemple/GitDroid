package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

/**
 * Элемент поискового ответа
 * @property name Имя файла
 * @property path Путь к файлу в дереве каталогов GitHub-репозитория
 * @property htmlUrl Ссылка на файл
 * @property repository Репозиторий, в котором хранится файл
 * @property score Релевантность ответа
 */
data class SearchResultItemData(
    @SerializedName("name") val name: String = "",
    @SerializedName("path") val path: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
    @SerializedName("repository") val repository: GHRepositoryData = GHRepositoryData(),
    @SerializedName("score") val score: Float = 0.0F,
)
