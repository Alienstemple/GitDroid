package com.example.gitdroid.models.domain

data class SearchResult(
    val totalResultCount: Long,
    val incompleteResults: Boolean,
    val searchResultItems: List<SearchResultItem>,
)
