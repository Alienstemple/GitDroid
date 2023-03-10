package com.example.gitdroid.data.room

import com.example.gitdroid.models.domain.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRoomRepository {
    fun getAllProjects(): Flow<List<Project>>

    fun getProjectById(id: String): Project

    suspend fun addProject(project: Project)

    suspend fun addAllProjects(projects: List<Project>)

    suspend fun deleteAll()
}