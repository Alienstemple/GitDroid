package com.example.gitdroid.data.projects

import android.util.Log
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.domain.projects.ProjectsRepository
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Имплементирует интерфейс [ProjectsRepository]
 * Отвечает за получение списка всех проектов, добавление, удаление и обновление проекта
 * @constructor Принимает экземпляры [ProjectConverter], [SearchResultItemConverter], [DatabaseReference]
 */
class ProjectsRepositoryImpl(
    private val projectConverter: ProjectConverter,
    private val searchResultItemConverter: SearchResultItemConverter,
    private val databaseReference: DatabaseReference,
) : ProjectsRepository {
    override fun getAllProjects() = callbackFlow<List<Project>> {
        val projectDatas = mutableListOf<ProjectData>()
        val projects = mutableListOf<Project>()

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.map {
                    // Default конструктор нужен для Project, SearchResItem, т к иначе Database error
                    projectDatas.add(it.getValue(ProjectData::class.java) ?: ProjectData("",
                        "",
                        emptyList()))
                }
                projectDatas.map {
                    projects.add(projectConverter.convert(it))
                }
                trySendBlocking(projects)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Database error while updating project!")
                trySendBlocking(emptyList())
            }
        }

        databaseReference.addListenerForSingleValueEvent(listener)
        awaitClose {
            databaseReference
                .removeEventListener(listener)
        }
    }

    override suspend fun addProject(project: Project): Project =
        withContext(Dispatchers.IO) {

            val projectId = databaseReference.push().key.toString()
            project.projectId = projectId

            databaseReference.child(projectId).setValue(projectConverter.convert(project)).await()

            databaseReference.child(projectId).setValue(projectConverter.convert(project))
                .addOnSuccessListener {
                    Log.d(TAG, "Project inserted successfully")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error while inserting project")
                }

            project
        }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {

        databaseReference.child(projectId).child("searchResList")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val searchResList = mutableListOf<SearchResultItemData>()
                    snapshot.children.map {
                        searchResList.add(it.getValue(SearchResultItemData::class.java)
                            ?: SearchResultItemData())
                    }
                    searchResList.add(searchResultItemConverter.convert(searchResultItem))  // Adding new item to retreived list

                    databaseReference.child(projectId).child("searchResList")
                        .setValue(searchResList)
                        .addOnSuccessListener {
                            Log.d(TAG, "Search res list updated successfully")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Error while updating search res list")
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "Database error while updating project!")
                }
            })
    }

    override suspend fun deleteProject(projectId: String) {
        databaseReference.child(projectId).removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "Project deleted successfully")
            }
            .addOnFailureListener {
                Log.d(TAG, "Error while deleting project")
            }
    }

    companion object {
        const val TAG = "ProjFireRepoLog"
    }
}