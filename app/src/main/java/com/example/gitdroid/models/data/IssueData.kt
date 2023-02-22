package com.example.gitdroid.models.data

data class IssueData(
    val id: Int,
    val number: Int,
    val title: String,
    val user: UserData,
    val comments: Int,
    val created_at: String,
    val updated_at: String,
    val body: String
)
