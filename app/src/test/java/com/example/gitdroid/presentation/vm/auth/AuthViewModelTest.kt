package com.example.gitdroid.presentation.vm.auth

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gitdroid.domain.auth.AuthCallback
import com.example.gitdroid.domain.auth.AuthInteractorImpl
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val authStateObserver: Observer<AuthState> = mockk(relaxed = true)

    private lateinit var authInteractor: AuthInteractorImpl
    private lateinit var authViewModel: AuthViewModel
    private val email = "stub@mail"
    private val authCallbackInstance: AuthCallback = mockk()
    private val stubAuthState: AuthState = AuthState.AUTHORIZED

    @Before
    fun setUp() {
        authInteractor = mockk {
            coEvery { signInWithGithubProvider(email, authCallbackInstance) } returns mockk()
            every { checkAuthorized() } returns true
            coEvery { logout() } returns mockk()
        }

        authViewModel = AuthViewModel(authInteractor)

        authViewModel.authState.observeForever(authStateObserver)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        authViewModel.authState.removeObserver(authStateObserver)
        unmockkStatic(Log::class)
    }

    @Test
    fun `checkAuthorized positive result`() {
        // act
        authViewModel.checkAuthorized()
        // assert
        verify { authStateObserver.onChanged(stubAuthState) }
    }

    @Test
    fun `signIn positive result`() {
        // act
        authViewModel.signInWithGithubProvider(email, authCallbackInstance)
        // assert
        verify { authStateObserver.onChanged(AuthState.AUTHORIZED) }
    }

    @Test
    fun `logout positive result`() {
        // act
        authViewModel.logout()
        // assert
        verify { authStateObserver.onChanged(AuthState.UNAUTHORIZED) }
    }
}