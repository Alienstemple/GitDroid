package com.example.gitdroid.domain

import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResult

interface ProjectsInteractor {
    suspend fun addProject(projectName: String)
    suspend fun listProjects(): MutableLiveData<MutableList<Project>>
}