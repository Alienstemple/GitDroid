package com.example.gitdroid.domain.projects

import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.flow.Flow

interface ProjectsInteractor {
    suspend fun addProject(projectName: String)
    suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem)
    suspend fun deleteProject(projectId: String)
    fun getAllProjects(): Flow<List<Project>>
}