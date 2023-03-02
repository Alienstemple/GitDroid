package com.example.gitdroid.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.models.domain.Project
import com.google.firebase.database.ValueEventListener

class ProjectsInteractorImpl(private val projectsFirebaseRepository: ProjectsFirebaseRepository): ProjectsInteractor {

    override fun addListener(listener: ValueEventListener) {
        projectsFirebaseRepository.addListener(listener)
    }

    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project(projectName, emptyList())
        projectsFirebaseRepository.addProject(newProject)
    }

    override suspend fun listProjects(): MutableLiveData<MutableList<Project>> {
        Log.d(TAG, "listProjects() called")
        return projectsFirebaseRepository.listProjects()
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}