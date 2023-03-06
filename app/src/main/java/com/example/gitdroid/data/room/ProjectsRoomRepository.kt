package com.example.gitdroid.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.gitdroid.models.domain.Project

class ProjectsRoomRepository(private val projectDao: ProjectDao) {

    fun getAllProjects(): LiveData<List<Project>> = projectDao.getAllProjects()

    fun getProjectById(id: String): Project = projectDao.getProjectById(id)

    suspend fun addProject(project: Project) {
        projectDao.addProject(project)
        Log.d(TAG, "Sucessf added to Room: $project")
    }

    suspend fun addAllProjects(projects: List<Project>) {
        projectDao.addAllProjects(projects)
    }

    suspend fun deleteAll() {
        projectDao.deleteAll()
    }

    companion object {
        const val TAG = "ProjRoomRepoLog"
    }
}

