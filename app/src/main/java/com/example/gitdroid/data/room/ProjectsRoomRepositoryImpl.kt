package com.example.gitdroid.data.room

import android.util.Log
import com.example.gitdroid.models.domain.Project
import kotlinx.coroutines.flow.Flow

class ProjectsRoomRepositoryImpl(private val projectDao: ProjectDao): ProjectsRoomRepository {

    override fun getAllProjects(): Flow<List<Project>> = projectDao.getAllProjects()

    override fun getProjectById(id: String): Project = projectDao.getProjectById(id)

    override suspend fun addProject(project: Project) {
        projectDao.addProject(project)
        Log.d(TAG, "Sucessf added to Room: $project")
    }

    override suspend fun addAllProjects(projects: List<Project>) {
        projectDao.addAllProjects(projects)
    }

    override suspend fun deleteAll() {
        projectDao.deleteAll()
    }

    companion object {
        const val TAG = "ProjRoomRepoLog"
    }
}

