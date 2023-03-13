package com.example.gitdroid.data.projects

import android.util.Log
import com.example.gitdroid.domain.projects.ProjectsFirebaseRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


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

    override suspend fun addProject(project: Project): Project =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "In addProject in repo")

            val projectId = databaseReference.push().key.toString()
            project.projectId = projectId
            Log.d(TAG, "ProjectId = $projectId")
            Log.d(TAG, "Project = $project")

            databaseReference.child(projectId).setValue(project)
                .addOnSuccessListener {
                    Log.d(TAG, "Project inserted successfully")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error while inserting project")
                }

            project
        }

    override suspend fun updateProject(project: Project, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() called with: project = $project")

        databaseReference.child(project.projectId).child("searchResList")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val searchResList = mutableListOf<SearchResultItem>()
                    snapshot.children.map {
                        searchResList.add(it.getValue(SearchResultItem::class.java)
                            ?: SearchResultItem())
                    }
                    Log.d(TAG, "Old search res list: $searchResList")
                    searchResList.add(searchResultItem)  // Adding new item to retreived list
                    Log.d(TAG, "New search res list: $searchResList")

                    databaseReference.child(project.projectId).child("searchResList")
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