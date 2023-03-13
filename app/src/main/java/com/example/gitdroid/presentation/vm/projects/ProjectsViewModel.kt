package com.example.gitdroid.presentation.vm.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
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
                // Default конструктор нужен для Project, SearchResItem, т к иначе Database error
                projects.add(it.getValue(Project::class.java) ?: Project("", "", emptyList()))  // TODO создание сделать норм
            }
            _projectList.postValue(projects)   // Обновленные данные - в LiveData
            viewModelScope.launch(IO) {
                projectsInteractor.deleteAllProjects()
                projectsInteractor.addAllProjects(projects)
            }

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

    fun allProjects(): Flow<List<Project>> {
        return projectsInteractor.getAllProjects()
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

    fun deleteProject(projectId: String) {
        Log.d(TAG, "deleteProject() called with: projectId = $projectId")
        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.deleteProject(projectId)
        }
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}