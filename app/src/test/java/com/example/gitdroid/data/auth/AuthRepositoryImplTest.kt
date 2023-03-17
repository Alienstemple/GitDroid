package com.example.gitdroid.data.auth

import android.util.Log
import com.example.gitdroid.domain.auth.AuthCallback
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AuthRepositoryImplTest {

    private val auth: FirebaseAuth = mockk()

    private val provider: OAuthProvider.Builder = mockk()

    private val sessionManager: SessionManager = mockk()

    private lateinit var authRepository: AuthRepositoryImpl

    private val stubEmail = "stub@mail"
    private val stubCallback: AuthCallback = mockk()
    private val paramKey: String = "login"
    private val paramValue: String = stubEmail
    private val stubOAuthProvider: OAuthProvider = mockk()
    private val stubAuthResult: AuthResult = mockk()
    private val stubOAuthCredential: OAuthCredential = mockk()
    private val stubAccessToken: String = "Access token"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { auth.currentUser } returns mockk()
        every { auth.signOut() } just runs
        every { sessionManager.removeAuthToken() } just runs
        every { provider.addCustomParameter(paramKey, paramValue) } returns mockk()
        every { provider.setScopes(any()) } returns mockk()
        every { provider.build() } returns stubOAuthProvider
        every { stubCallback.onRegister(auth, stubOAuthProvider) } returns stubAuthResult
        every { stubAuthResult.credential } returns stubOAuthCredential
        every { stubOAuthCredential.accessToken } returns stubAccessToken
        every { sessionManager.saveAuthToken(stubAccessToken) } just runs
        authRepository = AuthRepositoryImpl(auth, provider, sessionManager)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `checkAuthorized positive result`() {
        // act
        val actual = authRepository.checkAuthorized()
        // assert
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `checkAuthorized negative result`() {
        every { auth.currentUser } returns null
        // act
        val actual = authRepository.checkAuthorized()
        // assert
        assertThat(actual).isEqualTo(false)
    }

    @Test
    fun `logout positive result`() {
        // act
        authRepository.logout()
        // assert
        verify { auth.signOut() }
        verify { sessionManager.removeAuthToken() }
    }

    @Test
    fun `signIn positive result`() = runTest {
        // act
        authRepository.signIn(stubEmail, stubCallback)
        // assert
        verify { sessionManager.saveAuthToken(stubAccessToken) }
        verify { stubCallback.onRegister(auth, stubOAuthProvider) }
    }
}