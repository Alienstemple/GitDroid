package com.example.gitdroid.data.projects

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.google.common.truth.Truth.assertThat
import com.google.firebase.database.DatabaseReference
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before

internal class ProjectsRepositoryImplTest {

    @MockK
    private lateinit var projectConverter: ProjectConverter

    @MockK
    private lateinit var databaseReference: DatabaseReference

    @MockK
    private lateinit var searchResItemConverter: SearchResultItemConverter

    private lateinit var projectsRepositoryImpl: ProjectsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        projectsRepositoryImpl = ProjectsRepositoryImpl(projectConverter,
            searchResItemConverter,
            databaseReference)
    }

    @org.junit.jupiter.api.Test
    fun getAllProjects() = runTest {
    }

    @org.junit.jupiter.api.Test
    fun addProject() = runTest {

        //arrange
        val newProject = MiscCreator.createProject()

        // act
        val actual = projectsRepositoryImpl.addProject(newProject)

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