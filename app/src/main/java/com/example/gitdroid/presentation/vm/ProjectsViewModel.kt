package com.example.gitdroid.presentation.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.gitdroid.domain.GithubInteractor
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor): ViewModel() {

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>> = _projectList

    fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: userName = $projectName")

        viewModelScope.launch(Dispatchers.IO) {

            projectsInteractor.addProject(projectName)

//            _projectList.postValue(updatedList)  // Получить обновленные данные (потом заменить на listener)
        }
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}