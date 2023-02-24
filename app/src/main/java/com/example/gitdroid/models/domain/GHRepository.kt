package com.example.gitdroid.models.domain

data class GHRepository(
    val id: Number,
    val name: String,
    val full_name: String,
    val owner: User,
    val issue_comment_url: String,
    val issues_url: String,
    val updated_at: String,
    val language: String,
    val open_issues_count: Int
)
