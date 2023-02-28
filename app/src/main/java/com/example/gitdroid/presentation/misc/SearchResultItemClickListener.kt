package com.example.gitdroid.presentation.misc

import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.models.domain.SearchResultItem

interface SearchResultItemClickListener {
    fun onItemClicked(searchResultItem: SearchResultItem)
}