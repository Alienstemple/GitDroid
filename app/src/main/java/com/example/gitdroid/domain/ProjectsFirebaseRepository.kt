package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener

interface ProjectsFirebaseRepository {
    fun addListener(valueEventListener: ValueEventListener)
    suspend fun addProject(project: Project): Project
    suspend fun deleteProject(projectId: String) /* Что вернуть? Рез-т пустой */
    suspend fun updateProject(project: Project, searchResultItem: SearchResultItem)
}