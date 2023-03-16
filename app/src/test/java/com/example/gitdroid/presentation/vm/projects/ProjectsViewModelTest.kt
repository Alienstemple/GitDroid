package com.example.gitdroid.presentation.vm.projects

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gitdroid.MiscCreator
import com.example.gitdroid.domain.projects.ProjectsInteractorImpl
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

internal class ProjectsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val projectListObserver: Observer<List<Project>> = mockk(relaxed = true)
    private val projectLoadStateObserver: Observer<ProjectLoadState> = mockk(relaxed = true)

    private lateinit var projectInteractor: ProjectsInteractorImpl
    private lateinit var projectsViewModel: ProjectsViewModel

    private val projectName: String = "ProjName"
    private val projectId: String = "id"
    private val searchResultItem: SearchResultItem = MiscCreator.createSearchResultItem()
    private val projectList: List<Project> = MiscCreator.createProjectList()
    private val flow: Flow<List<Project>> = flowOf(projectList)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        projectInteractor = mockk {
            every { getAllProjects() } returns flow
            coEvery { addProject(projectName) } returns mockk()
            coEvery { updateProject(projectId, searchResultItem) } returns mockk()
            coEvery { deleteProject(projectId) } returns mockk()
        }

        projectsViewModel = ProjectsViewModel(projectInteractor)

        projectsViewModel.projectList.observeForever(projectListObserver)
        projectsViewModel.projectLoadState.observeForever(projectLoadStateObserver)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        projectsViewModel.projectList.removeObserver(projectListObserver)
        projectsViewModel.projectLoadState.removeObserver(projectLoadStateObserver)
        unmockkStatic(Log::class)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadAllProjects() {
        // act
        projectsViewModel.loadAllProjects()
        // assert
        verify { projectLoadStateObserver.onChanged(ProjectLoadState.LOADING) }
        coVerify { projectInteractor.getAllProjects() }
        verify { projectListObserver.onChanged(projectList) }
        verify { projectLoadStateObserver.onChanged(ProjectLoadState.COMPLETED) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addProject() {
        // act
        projectsViewModel.addProject(projectName)
        // assert
//        coVerify { projectInteractor.addProject(projectName) }
        verify { projectListObserver.onChanged(projectList) }
    }

    @Test
    fun updateProject() {
    }

    @Test
    fun deleteProject() {
    }
}