package com.example.gitdroid.domain.projects

import android.util.Log
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.flow.Flow

class ProjectsInteractorImpl(
    private val projectsRepository: ProjectsRepository,
) : ProjectsInteractor {

    override fun getAllProjects(): Flow<List<Project>> {
        return projectsRepository.getAllProjects()
    }

    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project("", projectName, emptyList())
        projectsRepository.addProject(newProject)
    }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        Log.d(TAG,
            "updateProject() called with: projectId = $projectId, searchResultItem = $searchResultItem")
        projectsRepository.updateProject(projectId,
            searchResultItem)
    }

    override suspend fun deleteProject(projectId: String) {
        Log.d(TAG, "deleteProject() called with: projectId = $projectId")
        projectsRepository.deleteProject(projectId)
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}