package com.example.gitdroid.data.search

import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsFirebaseRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.jupiter.api.Test

internal class NetworkRepositoryImplTest {

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

    @Test
    fun getCodeSearch() {
    }
}