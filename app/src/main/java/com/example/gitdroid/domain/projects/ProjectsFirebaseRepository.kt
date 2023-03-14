package com.example.gitdroid.domain.projects

import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface ProjectsFirebaseRepository {
    fun addListener(valueEventListener: ValueEventListener)
    fun getAllProjects(): Flow<List<Project>>
    suspend fun addProject(project: Project): Project
    suspend fun deleteProject(projectId: String) /* Что вернуть? Рез-т пустой */
    suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem)
}