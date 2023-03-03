package com.example.gitdroid.domain

import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResult
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener

interface ProjectsInteractor {
    fun addListener(listener: ValueEventListener)
    suspend fun addProject(projectName: String)
    suspend fun updateProject(projectName: String, searchResultItem: SearchResultItem)
}