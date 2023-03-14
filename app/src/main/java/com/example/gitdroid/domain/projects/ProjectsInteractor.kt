package com.example.gitdroid.domain.projects

import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResult
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface ProjectsInteractor {
    suspend fun addProject(projectName: String)
    suspend fun updateProject(projectName: String, searchResultItem: SearchResultItem)
    suspend fun deleteProject(projectId: String)
    suspend fun addAllProjects(projects: List<Project>)
    suspend fun deleteAllProjects()
    fun getAllProjects(): Flow<List<Project>>
}