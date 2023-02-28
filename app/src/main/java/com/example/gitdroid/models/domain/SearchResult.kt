package com.example.gitdroid.models.domain

data class SearchResult(
    val total_count: Long,
    val incomplete_results: Boolean,
    val items: List<SearchResultItem>,
)
