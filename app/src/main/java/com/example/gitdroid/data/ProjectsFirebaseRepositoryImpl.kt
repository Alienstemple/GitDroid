package com.example.gitdroid.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.models.domain.Project
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectsFirebaseRepositoryImpl : ProjectsFirebaseRepository {
    private val currentUserUid = Firebase.auth.currentUser!!.uid
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUserUid)

    override suspend fun addProject(project: Project): MutableLiveData<Project> =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "In addProject in repo")
            val result = MutableLiveData<Project>()
            val projectId = databaseReference.push().key.toString()
            Log.d(TAG, "ProjectId = $projectId")

            databaseReference.child(projectId)
                .updateChildren(mapOf(project.name to project.searchResList))
                .addOnSuccessListener {
                    Log.d(TAG, "Project inserted successfully")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error while inserting project")
                }

            result
        }

    override suspend fun listProjects(): MutableLiveData<MutableList<Project>> =
        withContext(Dispatchers.IO) {
            val result = MutableLiveData<MutableList<Project>>()
            // Firebase logic
            result
        }

    override suspend fun deleteProject(projectName: String) /* Что вернуть? Рез-т пустой */ =
        withContext(Dispatchers.IO) {
        // Firebase logic
        }

    override suspend fun updateProject(project: Project): MutableLiveData<Project> =
        withContext(Dispatchers.IO) {
            val result = MutableLiveData<Project>()
            // Firebase logic
            result
        }

    companion object {
        const val TAG = "ProjFireRepoLog"
    }
}