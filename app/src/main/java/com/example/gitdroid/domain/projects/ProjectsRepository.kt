package com.example.gitdroid.domain.projects

import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    fun getAllProjects(): Flow<List<Project>>
    suspend fun addProject(project: Project): Project
    suspend fun deleteProject(projectId: String)
    suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem)
}