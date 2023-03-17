package com.example.gitdroid.models.domain

/**
 * Результат поискового запроса
 * @property totalResultCount Общее число результатов поиска
 * @property incompleteResults Неполные результаты
 * @property searchResultItems Список элементов поисковой выдачи
 */
data class SearchResult(
    val totalResultCount: Long,
    val incompleteResults: Boolean,
    val searchResultItems: List<SearchResultItem>,
)
