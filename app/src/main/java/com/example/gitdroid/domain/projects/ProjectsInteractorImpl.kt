package com.example.gitdroid.domain.projects

import android.util.Log
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.search.room.ProjectsRoomRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProjectsInteractorImpl(
    private val projectsFirebaseRepository: ProjectsFirebaseRepository,
    private val projectsRoomRepository: ProjectsRoomRepository,
) : ProjectsInteractor {

    @ExperimentalCoroutinesApi
    override fun getAllProjects(): Flow<List<Project>> =
        projectsFirebaseRepository.getAllProjects()

    override suspend fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: projectName = $projectName")
        val newProject = Project("", projectName, emptyList())
        val addedProject = projectsFirebaseRepository.addProject(newProject)
        Log.d(TAG, "Project before inserting in DB: $addedProject")
        projectsRoomRepository.addProject(addedProject)
    }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {

        // TODO convert Project to ProjectData
        projectsFirebaseRepository.updateProject(projectId,
            searchResultItem)
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

    companion object {
        const val TAG = "ProjInteractLog"
    }
}