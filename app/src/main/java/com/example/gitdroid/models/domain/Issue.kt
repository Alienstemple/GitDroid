package com.example.gitdroid.models.domain

import com.example.gitdroid.models.data.UserData

data class Issue(
    val id: Int,
    val number: Int,
    val title: String,
    val user: UserData,
    val comments: Int,
    val created_at: String,
    val updated_at: String,
    val body: String
)
