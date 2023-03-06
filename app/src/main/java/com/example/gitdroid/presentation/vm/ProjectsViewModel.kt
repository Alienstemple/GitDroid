package com.example.gitdroid.presentation.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor) : ViewModel() {

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>> = _projectList

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.d(TAG, "Data changed!")
            val projects = mutableListOf<Project>()
            snapshot.children.map {
                Log.d(TAG, "Snapshot's child: ${it.value.toString()}")
                projects.add(it.getValue(Project::class.java) ?: Project("", "", emptyList()))  // TODO создание сделать норм
            }
            _projectList.postValue(projects)   // Обновленные данные - в LiveData
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d(TAG, "Database error: ${error.code} ${error.message}")
        }
    }

    init {
        // При инициализации VM создаем listener и передаем в interactor - Firebase repo
        Log.d(TAG, "Init Projects VM: before adding listener")
        projectsInteractor.addListener(listener)
    }

    fun testMethod() {  // For testin shared vm
        Log.d(TAG, "testMethod() called")
    }

    fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: userName = $projectName")

        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.addProject(projectName)
        }
    }

    fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() called with: projectId = $projectId")
        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.updateProject(projectId, searchResultItem)
        }
    }

    override fun onCleared() {
        // TODO remove listener
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}