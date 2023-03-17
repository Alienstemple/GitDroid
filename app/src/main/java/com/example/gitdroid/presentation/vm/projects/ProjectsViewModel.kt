package com.example.gitdroid.presentation.vm.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitdroid.domain.projects.ProjectsInteractor
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.ProjectLoadState
import com.example.gitdroid.models.domain.SearchResultItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * ViewModel проектов. Осуществляет обновление текущего списка проектов [projectList],
 * добавляет, обновляет и удалаяет проекты.
 * Хранит список всех проектов, состояние загрузки списка проектов
 * и результат обновления проекта в LiveData
 */
class ProjectsViewModel(private val projectsInteractor: ProjectsInteractor) : ViewModel() {

    private val _projectList = MutableLiveData<List<Project>>()

    /**
     * Список всех проектов пользователя
     */
    val projectList: LiveData<List<Project>> = _projectList

    private val _projectLoadState = MutableLiveData<ProjectLoadState>()

    /**
     * Состояние загрузки списка проектов [ProjectLoadState]
     */
    val projectLoadState: LiveData<ProjectLoadState> = _projectLoadState

    private val _projectUpdated = MutableLiveData<Boolean>()

    /**
     * Триггерит сообщение об успешном обновлении проекта (при добавлении к нему нового результата поиска)
     */
    val projectUpdated: LiveData<Boolean> = _projectUpdated

    /**
     * Загрузка текущего списка всех проектов. Обработка ошибок с помощью [projectLoadState]
     */
    fun loadAllProjects() {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "Exception thrown in one of the children. $exception")
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

    /**
     * Добавление нового проекта с пустым списом поисковых результатов.
     * @param projectName имя проекта
     */
    fun addProject(projectName: String) {
        Log.d(TAG, "addProject() called with: userName = $projectName")

        viewModelScope.launch(IO) {
            projectsInteractor.addProject(projectName)
            getAllProjects()
        }
    }

    /**
     * Обновление проекта, то есть добавление нового элемента в его список результатов поиска.
     * @param projectId ID проекта
     * @param searchResultItem Новый результат поиска
     */
    fun updateProject(projectId: String, searchResultItem: SearchResultItem) {
        Log.d(TAG, "updateProject() called with: projectId = $projectId")
        viewModelScope.launch(IO) {
            projectsInteractor.updateProject(projectId, searchResultItem)
            _projectUpdated.postValue(true)
            getAllProjects()
        }
    }

    /**
     * Сброс состояния обновления. Вызывается после [updateProject]
     */
    fun clearAddState() {
        _projectUpdated.postValue(false)
    }

    /**
     * Удаление проекта
     */
    fun deleteProject(projectId: String) {
        Log.d(TAG, "deleteProject() called with: projectId = $projectId")
        viewModelScope.launch(IO) {
            projectsInteractor.deleteProject(projectId)
            getAllProjects()
        }
    }

    companion object {
        const val TAG = "RepoVMLog"
    }
}