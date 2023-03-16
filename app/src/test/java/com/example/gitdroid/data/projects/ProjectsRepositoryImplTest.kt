package com.example.gitdroid.data.projects

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.models.domain.SearchResultItem
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProjectsRepositoryImplTest {

    @MockK
    private lateinit var projectConverter: ProjectConverter

    @MockK
    private lateinit var databaseReference: DatabaseReference

    @MockK
    private lateinit var searchResItemConverter: SearchResultItemConverter

    private lateinit var projectsRepositoryImpl: ProjectsRepositoryImpl

    private val stubProjectId: String = "id"
    private val stubSearchResultItem: SearchResultItem = MiscCreator.createSearchResultItem()
    private val pathString = "searchResList"
    private val stubDatabaseReference: DatabaseReference = mockk()
    private val secondStubDatabaseReference: DatabaseReference = mockk()
    private val thirdStubDatabaseReference: DatabaseReference = mockk()
    private val stubValueEventListener: ValueEventListener = mockk()
    private val stubTask: Task<Void> = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { databaseReference.child(stubProjectId) } returns stubDatabaseReference
        every { stubDatabaseReference.removeValue() } returns stubTask
//        every { stubTask.addOnSuccessListener(mockk()) } answers { }
//        every { stubDatabaseReference.child(pathString) } returns secondStubDatabaseReference  // FIXME doesn't work
        every { /*thirdStubDatabaseReference*//*secondStubDatabaseReference*/stubDatabaseReference.addListenerForSingleValueEvent(stubValueEventListener) } just runs

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

    @Test
    fun updateProject() = runTest{
        // act
//        projectsRepositoryImpl.updateProject(stubProjectId, stubSearchResultItem)
        // assert

    }

    @Test
    fun deleteProject() = runTest{
//        projectsRepositoryImpl.deleteProject(stubProjectId)
    }
}