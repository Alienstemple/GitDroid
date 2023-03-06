package com.example.gitdroid.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener

class ProjectsInteractorImpl(private val projectsFirebaseRepository: ProjectsFirebaseRepository,
                             private val projectsRoomRepository: ProjectsRoomRepository): ProjectsInteractor {

    override fun addListener(listener: ValueEventListener) {
        projectsFirebaseRepository.addListener(listener)
    }

    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project("", projectName, emptyList())
        val addedProject = projectsFirebaseRepository.addProject(newProject)
        Log.d(TAG, "Project before inserting in DB: $addedProject")
        projectsRoomRepository.addProject(addedProject)
    }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {

        // TODO Get Project from local database
        projectsFirebaseRepository.updateProject(Project())
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}