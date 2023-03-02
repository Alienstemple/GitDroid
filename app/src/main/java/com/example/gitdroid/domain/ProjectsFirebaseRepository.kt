package com.example.gitdroid.domain

import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.google.firebase.database.ValueEventListener

interface ProjectsFirebaseRepository {
    fun addListener(valueEventListener: ValueEventListener)
    suspend fun addProject(project: Project): MutableLiveData<Project>
    suspend fun listProjects(): MutableLiveData<MutableList<Project>>
    suspend fun deleteProject(projectName: String) /* Что вернуть? Рез-т пустой */
    suspend fun updateProject(project: Project): MutableLiveData<Project>
}