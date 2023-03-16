package com.example.gitdroid.domain.projects

import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.flow.Flow

/**
 * Взаимодействует с репозиторием проектов [ProjectsRepository] для получения списка всех проектов, добавления, удаления и обновления проекта
 */
interface ProjectsInteractor {

    /**
     * Добавление нового проекта по имени
     * @param projectName
     */
    suspend fun addProject(projectName: String)

    /**
     * Обновление проекта (добавление к текущему списку результатов поиска нового элемента)
     * @param projectId
     * @param searchResultItem
     */
    suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem)

    /**
     * Удаление проекта
     * @param projectId
     */
    suspend fun deleteProject(projectId: String)

    /**
     * Получение списка всех проектов в виде [Flow]
     */
    fun getAllProjects(): Flow<List<Project>>
}