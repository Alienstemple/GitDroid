package com.example.gitdroid.presentation.vm.projects

import android.util.Log
import androidx.lifecycle.*
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.models.data.ProjectData
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor) : ViewModel() {

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>> = _projectList

    fun loadAllProjects() {
        viewModelScope.launch(IO) {
            getAllProjects()
        }
    }

    private suspend fun getAllProjects() {
        Log.d(TAG, "Load all projs.")
        projectsInteractor.getAllProjects().collect {
            Log.d(TAG, "In collect: $it.")
            _projectList.postValue(it)
            Log.d(TAG, "After post proj list has ${_projectList.value?.size} items")
        }
    }

    fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: userName = $projectName")

        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.addProject(projectName)
            getAllProjects()
        }
    }

    fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() called with: projectId = $projectId")
        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.updateProject(projectId, searchResultItem)
            getAllProjects()
        }
    }

    fun deleteProject(projectId: String) {
        Log.d(TAG, "deleteProject() called with: projectId = $projectId")
        viewModelScope.launch(Dispatchers.IO) {
            projectsInteractor.deleteProject(projectId)
            getAllProjects()
        }
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}