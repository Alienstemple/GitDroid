package com.example.gitdroid.data.projects

import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository
import com.example.gitdroid.models.data.SearchResultItemData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.vm.projects.ProjectsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf


class ProjectsFirebaseRepositoryImpl : ProjectsFirebaseRepository {
    private val currentUserUid = Firebase.auth.currentUser!!.uid
    private val databaseReference =
        FirebaseDatabase.getInstance().getReference("users").child(currentUserUid)

    private var listener: ValueEventListener? = null

    override fun addListener(valueEventListener: ValueEventListener) {
        listener = valueEventListener
        databaseReference.addValueEventListener(listener!!)
        Log.d(TAG, "Listener added to firebase repo!")
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllProjects() = callbackFlow<List<Project>> {
        Log.d(TAG, "getAllProjects() called")
        val projects = mutableListOf<Project>()

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.map {
                    Log.d(ProjectsViewModel.TAG, "Snapshot's child: ${it.value.toString()}")
                    // TODO Convert
                    // Default конструктор нужен для Project, SearchResItem, т к иначе Database error
                    projects.add(it.getValue(Project::class.java) ?: Project("", "", emptyList()))  // TODO создание сделать норм
                }
                Log.d(TAG, "Projects from Firebase: $projects")
                this@callbackFlow.trySendBlocking(projects)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Database error while updating project!")
                this@callbackFlow.trySendBlocking(emptyList())
            }
        }

//        databaseReference.addListenerForSingleValueEvent (
//            listener
//        )

        databaseReference.addValueEventListener (
            listener
        )

        awaitClose {  // FIXME crashes
            Log.d(TAG, "awaitClose() called")
            databaseReference
                .removeEventListener(listener)
        }
    }

    override suspend fun addProject(project: Project): Project =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "In addProject in repo")

            val projectId = databaseReference.push().key.toString()
            project.projectId = projectId
            Log.d(TAG, "ProjectId = $projectId")
            Log.d(TAG, "Project = $project")

            databaseReference.child(projectId).setValue(project)  // TODO convert to data
                .addOnSuccessListener {
                    Log.d(TAG, "Project inserted successfully")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error while inserting project")
                }

            project
        }

    override suspend fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() called with: project = $projectId")

        databaseReference.child(projectId).child("searchResList")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val searchResList = mutableListOf<SearchResultItem>()  // TODO ItemData
                    snapshot.children.map {
                        searchResList.add(it.getValue(SearchResultItem::class.java)
                            ?: SearchResultItem())
                    }
                    Log.d(TAG, "Old search res list: $searchResList")
                    searchResList.add(searchResultItem)  // Adding new item to retreived list
                    Log.d(TAG, "New search res list: $searchResList")

                    databaseReference.child(projectId).child("searchResList")
                        .setValue(searchResList)
                        .addOnSuccessListener {
                            Log.d(TAG, "Search res list updated successfully")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Error while updating search res list")
                        }

                   val a = flowOf("a")
                    a.asLiveData()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "Database error while updating project!")
                }

            })
    }

    override suspend fun deleteProject(projectId: String) { /* Что вернуть? Рез-т пустой */
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