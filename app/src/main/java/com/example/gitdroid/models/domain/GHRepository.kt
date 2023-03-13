package com.example.gitdroid.models.domain

data class GHRepository(
    val repoId: Long = 0,
    val repoName: String = "",
    val repoFullName: String = "",
    val repoOwner: User = User()
)
