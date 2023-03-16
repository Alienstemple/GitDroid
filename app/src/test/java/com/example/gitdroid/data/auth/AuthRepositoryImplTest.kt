package com.example.gitdroid.data.auth

import android.util.Log
import com.example.gitdroid.domain.auth.AuthCallback
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any

internal class AuthRepositoryImplTest {

    @MockK
    private lateinit var auth: FirebaseAuth

    @MockK
    private lateinit var provider: OAuthProvider.Builder

    @MockK
    private lateinit var sessionManager: SessionManager

    private lateinit var authRepository: AuthRepositoryImpl

    private val firebaseUser: FirebaseUser = mockk()
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
        every { auth.currentUser } returns firebaseUser
        every { auth.signOut() } just runs
        every { sessionManager.removeAuthToken() } just runs
        every { provider.addCustomParameter(paramKey, paramValue) } returns mockk()
        every { provider.setScopes(any()) } returns mockk()
        every { provider.build() } returns stubOAuthProvider
        every { stubCallback.onRegister(auth, stubOAuthProvider) } returns stubAuthResult
        every { stubAuthResult.credential } returns stubOAuthCredential
        every { stubOAuthCredential.accessToken } returns stubAccessToken
        every { sessionManager.saveAuthToken(stubAccessToken) } returns mockk()
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
    fun `logout positive result`() {
        // act
        authRepository.logout()
        // assert
        verify { auth.signOut() }
        verify { sessionManager.removeAuthToken() }
    }

    @Test
    fun `signInWithGithubProvider positive result`() = runTest {
        // act
        authRepository.signInWithGithubProvider(stubEmail, stubCallback)
        // assert
        verify { sessionManager.saveAuthToken(stubAccessToken) }
    }
}