package com.example.gitdroid.data.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitdroid.models.domain.Project

class ProjectsRoomRepository(private val projectDao: ProjectDao) {

    fun getAllProjects(): LiveData<List<Project>> = projectDao.getAllProjects()

    fun getProjectById(id: String): LiveData<Project> = projectDao.getProjectById(id)

    suspend fun addProject(project: Project) {
        projectDao.addProject(project)
    }

    suspend fun addAllProjects(projects: List<Project>) {
        projectDao.addAllProjects(projects)
    }

    suspend fun deleteAll() {
        projectDao.deleteAll()
    }
}

