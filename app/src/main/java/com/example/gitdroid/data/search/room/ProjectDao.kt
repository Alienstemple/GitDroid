package com.example.gitdroid.data.search.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitdroid.models.domain.Project
import kotlinx.coroutines.flow.Flow


@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProject(project: Project)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllProjects(projects: List<Project>)
    @Query("SELECT * FROM projects WHERE id=:id")
    fun getProjectById(id: String): Project
    @Query("DELETE FROM projects")
    suspend fun deleteAll()
    @Query("SELECT * FROM projects")
    fun getAllProjects(): Flow<List<Project>>
}
