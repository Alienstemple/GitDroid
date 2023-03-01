package com.example.gitdroid.domain

import android.util.Log
import com.example.gitdroid.models.domain.Project

class ProjectsInteractorImpl(private val projectsFirebaseRepository: ProjectsFirebaseRepository): ProjectsInteractor {
    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project(projectName, emptyList())
        projectsFirebaseRepository.addProject(newProject)
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}