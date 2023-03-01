package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.SearchResult

interface ProjectsInteractor {
    suspend fun addProject(projectName: String)
}