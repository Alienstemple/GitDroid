package com.example.gitdroid.domain.search

import com.example.gitdroid.MiscCreator
import com.example.gitdroid.data.search.NetworkRepositoryImpl
import com.example.gitdroid.models.domain.SearchResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GithubInteractorImplTest {

    private val networkRepository: NetworkRepositoryImpl = mockk()

    private lateinit var githubInteractorImpl: GithubInteractorImpl

    private val stubSearchResult: SearchResult = MiscCreator.createSearchResult()
    private val stubQuery = "Search query"

    @Before
    fun setUp() {
        coEvery {
            networkRepository.getCodeSearch(stubQuery)
        } returns stubSearchResult
        githubInteractorImpl = GithubInteractorImpl(networkRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCodeSearch() = runTest {
        // act
        val actual = githubInteractorImpl.getCodeSearch(stubQuery)
        // assert
        assertThat(actual).isEqualTo(stubSearchResult)
        coVerify { networkRepository.getCodeSearch(stubQuery) }
    }
}