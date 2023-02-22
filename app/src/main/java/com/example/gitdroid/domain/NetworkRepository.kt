package com.example.gitdroid.domain

import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.Issue

interface NetworkRepository {
    suspend fun getReposByUser(name: String): List<GHRepository>
    suspend fun getIssuesByUserAndRepository(name: String, repo: String): List<Issue>
}