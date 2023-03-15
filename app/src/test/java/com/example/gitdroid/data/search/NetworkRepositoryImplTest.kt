package com.example.gitdroid.data.search

import android.util.Log
import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.converters.ProjectConverter
import com.example.gitdroid.data.converters.SearchResultConverter
import com.example.gitdroid.data.converters.SearchResultItemConverter
import com.example.gitdroid.data.projects.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.models.data.SearchResultData
import com.example.gitdroid.models.domain.SearchResult
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class NetworkRepositoryImplTest {

    private lateinit var searchResultConverter: SearchResultConverter

    private lateinit var networkService: NetworkService

    private lateinit var networkRepositoryImpl: NetworkRepositoryImpl

    private val stubSearchResult: SearchResult = MiscCreator.createSearchResult()
    private val stubSearchResultData: SearchResultData = MiscCreator.createSearchResultData()
    private val stubQuery = "Search query"

    @Before
    fun setUp() {
        searchResultConverter = mockk {
            coEvery {
                convert(stubSearchResultData)
            } returns stubSearchResult
        }
        networkService = mockk {
            coEvery {
                getCodeSearch(stubQuery)
            } returns stubSearchResultData
        }
        networkRepositoryImpl = NetworkRepositoryImpl(networkService, searchResultConverter)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get code search positive result`() = runTest {
        // act
        val actual = networkRepositoryImpl.getCodeSearch(stubQuery)
        // assert
        assertThat(actual).isEqualTo(stubSearchResult)
        coVerify {
            networkService.getCodeSearch(stubQuery)
        }
    }
}