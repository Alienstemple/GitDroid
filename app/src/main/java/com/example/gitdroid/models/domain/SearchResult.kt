package com.example.gitdroid.models.domain

/**
 * Результат поискового запроса
 */
data class SearchResult(
    val totalResultCount: Long,
    val incompleteResults: Boolean,
    val searchResultItems: List<SearchResultItem>,
)
