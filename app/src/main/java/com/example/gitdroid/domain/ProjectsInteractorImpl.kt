package com.example.gitdroid.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow

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

        val retreivedProject = projectsRoomRepository.getProjectById(projectId)
        Log.d(TAG, "Retreived project = $retreivedProject")
        Log.d(TAG, "Retreived project with new SearchResItem = $retreivedProject")
        projectsFirebaseRepository.updateProject(retreivedProject, searchResultItem)
    }

    override suspend fun deleteProject(projectId: String) {
        Log.d(TAG, "deleteProject() called with: projectId = $projectId")
        projectsFirebaseRepository.deleteProject(projectId)
    }

    override suspend fun deleteAllProjects() {
        Log.d(TAG, "deleteAllProjects() called")
        projectsRoomRepository.deleteAll()
    }

    override suspend fun addAllProjects(projects: List<Project>) {
        Log.d(TAG, "addAllProjects() called with: projects = $projects")
        projectsRoomRepository.addAllProjects(projects)
    }

    override fun getAllProjects(): Flow<List<Project>> {
        Log.d(TAG, "getAllProjects() called")
        return projectsRoomRepository.getAllProjects()
    }

    companion object {
        const val TAG = "ProjInteractLog"
    }
}