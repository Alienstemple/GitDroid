package com.example.gitdroid.models.data

/**
 * Проект ("папка", в которой храним результаты поиска)
 */
data class ProjectData(
    var id: String = "",
    var name: String = "",
    var searchResList: List<SearchResultItemData> = emptyList(),
)
