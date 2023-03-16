package com.example.gitdroid.domain.projects

import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.flow.Flow

/**
 * Имплементирует интерфейс [ProjectsInteractor]
 * Взаимодействует с репозиторием проектов [ProjectsRepository] для получения списка всех проектов, добавление, удаление и обновление проекта
 * @constructor [projectsRepository]
 */
class ProjectsInteractorImpl(
    private val projectsRepository: ProjectsRepository,
) : ProjectsInteractor {

    override fun getAllProjects(): Flow<List<Project>> {
        return projectsRepository.getAllProjects()
    }

    override suspend fun addProject(projectName: String) {
        val newProject = Project("", projectName, emptyList())
        projectsRepository.addProject(newProject)
    }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        projectsRepository.updateProject(projectId,
            searchResultItem)
    }

    override suspend fun deleteProject(projectId: String) {
        projectsRepository.deleteProject(projectId)
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}