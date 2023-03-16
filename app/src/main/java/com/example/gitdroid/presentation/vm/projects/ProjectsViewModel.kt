package com.example.gitdroid.presentation.vm.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor) : ViewModel() {

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>> = _projectList

    private val _projectLoadState = MutableLiveData<ProjectLoadState>()
    val projectLoadState: LiveData<ProjectLoadState> = _projectLoadState
    fun loadAllProjects() {

        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children. $exception")
            _projectLoadState.postValue(ProjectLoadState.ERROR)
        }

        viewModelScope.launch(SupervisorJob() + IO + handler) {
            _projectLoadState.postValue(ProjectLoadState.LOADING)
            getAllProjects()
        }
    }

    private suspend fun getAllProjects() {
        Log.d(TAG, "Load all projs.")
        projectsInteractor.getAllProjects().collect {
            Log.d(TAG, "In collect: $it.")
            _projectList.postValue(it)
            _projectLoadState.postValue(ProjectLoadState.COMPLETED)
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