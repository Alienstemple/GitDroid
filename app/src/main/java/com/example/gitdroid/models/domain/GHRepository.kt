package com.example.gitdroid.models.domain

data class GHRepository(
    val id: Number = 0,
    val name: String = "",
    val full_name: String = "",
    val owner: User = User(),
    val issue_comment_url: String = "",
    val issues_url: String = "",
    val updated_at: String = "",
    val language: String = "",
    val open_issues_count: Int = 0
)
