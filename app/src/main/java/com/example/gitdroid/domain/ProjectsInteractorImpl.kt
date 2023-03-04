package com.example.gitdroid.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener

class ProjectsInteractorImpl(private val projectsFirebaseRepository: ProjectsFirebaseRepository): ProjectsInteractor {

    override fun addListener(listener: ValueEventListener) {
        projectsFirebaseRepository.addListener(listener)
    }

    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project("", projectName, emptyList())
        projectsFirebaseRepository.addProject(newProject)
    }

    override suspend fun updateProject(projectName: String, searchResultItem: SearchResultItem) {
        Log.d(TAG,
            "updateProject() called with: projectName = $projectName, searchResultItem = $searchResultItem")
        projectsFirebaseRepository.updateProject(projectName, searchResultItem)
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}