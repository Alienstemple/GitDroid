package com.example.gitdroid.models.data

import com.google.gson.annotations.SerializedName

/**
 * Результат поискового запроса
 * @property totalCount Общее число результатов поиска
 * @property incompleteResults Неполные результаты
 * @property items Список элементов поисковой выдачи
 */
data class SearchResultData(
    @SerializedName("total_count") val totalCount: Long,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<SearchResultItemData>,
)
