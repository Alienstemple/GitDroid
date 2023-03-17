package com.example.gitdroid.domain.auth

import com.example.gitdroid.data.auth.AuthRepositoryImpl
import com.example.gitdroid.data.projects.ProjectsRepositoryImpl
import com.example.gitdroid.domain.projects.ProjectsInteractorImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AuthInteractorImplTest {

    private val authRepository: AuthRepositoryImpl = mockk()

    private lateinit var authInteractorImpl: AuthInteractorImpl

    private val stubEmail = "stub@mail"
    private val stubCallback: AuthCallback = mockk()

    @Before
    fun setUp() {

        every { authRepository.checkAuthorized() } returns true
        coEvery { authRepository.signIn(stubEmail, stubCallback) } just runs
        every { authRepository.logout() } just runs
        authInteractorImpl = AuthInteractorImpl(authRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkAuthorized_positive() {
        // act
        val actual = authInteractorImpl.checkAuthorized()
        // assert
        verify { authRepository.checkAuthorized() }
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun checkAuthorized_negative() {
        every { authRepository.checkAuthorized() } returns false
        // act
        val actual = authInteractorImpl.checkAuthorized()
        // assert
        verify { authRepository.checkAuthorized() }
        assertThat(actual).isEqualTo(false)
    }

    @Test
    fun signIn() = runTest {
        // act
        authInteractorImpl.signIn(stubEmail, stubCallback)
        // assert
        coVerify { authRepository.signIn(stubEmail, stubCallback) }
    }

    @Test
    fun logout() = runTest {
        // act
        authInteractorImpl.logout()
        // assert
        coVerify { authRepository.logout() }
    }
}