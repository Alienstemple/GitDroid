package com.example.gitdroid.models.domain

/**
 * Проект ("папка", в которой храним результаты поиска)
 * @property projectId Идентификатор
 * @property projectName Имя проекта
 * @property searchResList Список результатов поиска
 */
data class Project(
    var projectId: String,
    var projectName: String,
    var searchResList: List<SearchResultItem> = emptyList(),
)
