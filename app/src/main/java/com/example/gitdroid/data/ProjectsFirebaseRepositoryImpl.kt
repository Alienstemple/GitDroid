package com.example.gitdroid.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.example.gitdroid.presentation.vm.ProjectsViewModel
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

    fun removeListener() {
        listener?.let { databaseReference.removeEventListener(it) }
    }

    override suspend fun addProject(project: Project): Project =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "In addProject in repo")

            val projectId = databaseReference.push().key.toString()
            project.id = projectId
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

        databaseReference.child(project.id).child("searchResList")
            .addListenerForSingleValueEvent( object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val searchResList = mutableListOf<SearchResultItem>()
                    snapshot.children.map {
                        searchResList.add(it.getValue(SearchResultItem::class.java) ?: SearchResultItem())
                    }
                    Log.d(TAG, "Old search res list: $searchResList")
                    searchResList.add(searchResultItem)  // Adding new item to retreived list
                    Log.d(TAG, "New search res list: $searchResList")

                    databaseReference.child(project.id).child("searchResList")
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

    override suspend fun deleteProject(projectName: String) /* Что вернуть? Рез-т пустой */ =
        withContext(Dispatchers.IO) {
            // Firebase logic
        }

    companion object {
        const val TAG = "ProjFireRepoLog"
    }
}