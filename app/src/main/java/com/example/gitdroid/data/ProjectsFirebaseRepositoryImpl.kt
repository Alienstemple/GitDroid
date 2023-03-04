package com.example.gitdroid.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitdroid.domain.ProjectsFirebaseRepository
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

    fun removeListener() {
        listener?.let { databaseReference.removeEventListener(it) }
    }

    override suspend fun addProject(project: Project) {
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
        }
    }

    override suspend fun updateProject(projectName: String, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() FirebaseRepo called")

        val currentProject: Pair<String, Project> = CoroutineScope(Dispatchers.IO).async {
            getCurrentProject(projectName).value ?: ("" to Project())
        }.await()



        Log.d(TAG, "Project outside of listener: $currentProject")

        val modifiedProject = Project(currentProject.second.id, currentProject.second.name,
            currentProject.second.searchResList + searchResultItem)

        databaseReference.child(currentProject.first).setValue(modifiedProject)
            .addOnSuccessListener {
                Log.d(TAG, "Project updated successfully: $modifiedProject")
            }
            .addOnFailureListener {
                Log.d(TAG, "Error while updating project")
            }
    }

    private fun getCurrentProject(projectName: String): MutableLiveData<Pair<String, Project>> {
        val result = MutableLiveData<Pair<String, Project>>()

        databaseReference.orderByChild("name").equalTo(projectName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.map {
                        val key = it.key.toString()
                        val proj = it.getValue(Project::class.java) ?: Project()
                        result.postValue(key to proj)
                        Log.d(TAG, "Project found! key = $key $proj")
                    }.first()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "DB error")
                }
            })

        // Не дожидается завершения вызова и возвращает пустую Live Data

        return result
    }

    override suspend fun deleteProject(projectName: String) /* Что вернуть? Рез-т пустой */ =
        withContext(Dispatchers.IO) {
            // Firebase logic
        }

    companion object {
        const val TAG = "ProjFireRepoLog"
    }
}