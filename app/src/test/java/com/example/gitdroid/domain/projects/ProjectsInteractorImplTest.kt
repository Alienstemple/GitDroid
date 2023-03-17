package com.example.gitdroid.domain.projects

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.projects.ProjectsRepositoryImpl
import com.example.gitdroid.models.domain.Project
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProjectsInteractorImplTest {

    private val projectsRepository: ProjectsRepositoryImpl = mockk()

    private lateinit var projectsInteractorImpl: ProjectsInteractorImpl

    private val stubAllProjects: Flow<List<Project>> = flowOf(MiscCreator.createProjectList())
    private val stubProject: Project = MiscCreator.createProject()
    private val stubResProject: Project = MiscCreator.createProject()
    private val stubProjectId = "id"
    private val stubSearchResultItem: SearchResultItem = MiscCreator.createSearchResultItem()

    @Before
    fun setUp() {
        every { projectsRepository.getAllProjects() } returns stubAllProjects
        coEvery { projectsRepository.addProject(stubProject) } returns stubResProject
        coEvery { projectsRepository.updateProject(stubProjectId, stubSearchResultItem) } just runs
        coEvery { projectsRepository.deleteProject(stubProjectId) } just runs

        projectsInteractorImpl = ProjectsInteractorImpl(projectsRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllProjects() {
        // act
        val actual = projectsInteractorImpl.getAllProjects()
        // assert
        verify { projectsRepository.getAllProjects() }
        assertThat(actual).isEqualTo(stubAllProjects)
    }

    @Test
    fun addProject() = runTest {
        // act
        projectsInteractorImpl.addProject(stubProject.projectName)
        // assert
        coVerify { projectsRepository.addProject(stubProject) }
    }

    @Test
    fun updateProject() = runTest {
        projectsInteractorImpl.updateProject(stubProjectId, stubSearchResultItem)
        // assert
        coVerify { projectsRepository.updateProject(stubProjectId, stubSearchResultItem) }
    }

    @Test
    fun deleteProject() = runTest {
        projectsInteractorImpl.deleteProject(stubProjectId)
        // assert
        coVerify { projectsRepository.deleteProject(stubProjectId) }
    }
}