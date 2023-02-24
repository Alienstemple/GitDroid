package com.example.gitdroid.presentation.misc

import com.example.gitdroid.models.domain.GHRepository

interface RepositoryItemClickListener {
    fun onItemClicked(ghRepository: GHRepository)
}