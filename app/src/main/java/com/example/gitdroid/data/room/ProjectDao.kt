package com.example.gitdroid.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitdroid.models.domain.Project


@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProject(project: Project)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllProjects(projects: List<Project>)
    @Query("SELECT * FROM projects WHERE id=:id")
    fun getProjectById(id: String): Project  // TODO что лучше вернуть?
    @Query("DELETE FROM projects")
    suspend fun deleteAll()
    @Query("SELECT * FROM projects")
    fun getAllProjects(): LiveData<List<Project>>
}
