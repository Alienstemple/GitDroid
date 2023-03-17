package com.example.gitdroid.models.data

/**
 * Проект ("папка", в которой храним результаты поиска)
 * @property id Идентификатор
 * @property name Имя проекта
 * @property searchResList Список результатов поиска
 */
data class ProjectData(
    var id: String = "",
    var name: String = "",
    var searchResList: List<SearchResultItemData> = emptyList(),
)
