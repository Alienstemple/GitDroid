package com.example.gitdroid.models.domain

/**
 * Проект ("папка", в которой храним результаты поиска)
 */
data class Project(
    var projectId: String,
    var projectName: String,
    var searchResList: List<SearchResultItem> = emptyList(),
)
