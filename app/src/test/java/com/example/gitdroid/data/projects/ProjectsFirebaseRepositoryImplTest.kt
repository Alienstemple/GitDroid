package com.example.gitdroid.data.projects

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before

internal class ProjectsFirebaseRepositoryImplTest {

    @MockK
    private lateinit var projectConverter: ProjectConverter

    @MockK
    private lateinit var searchResItemConverter: SearchResultItemConverter

    private lateinit var projectsFirebaseRepositoryImpl: ProjectsFirebaseRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        projectsFirebaseRepositoryImpl = ProjectsFirebaseRepositoryImpl(projectConverter, searchResItemConverter)
    }

    @org.junit.jupiter.api.Test
    fun getAllProjects() = runTest {
    }

    @org.junit.jupiter.api.Test
    fun addProject() = runTest {

        //arrange
        val newProject = MiscCreator.createProject()

        // act
        val actual = projectsFirebaseRepositoryImpl.addProject(newProject)

        // assert
        assertThat(actual).isEqualTo(newProject)
    }

    @org.junit.jupiter.api.Test
    fun updateProject() {
    }

    @org.junit.jupiter.api.Test
    fun deleteProject() {
    }
}