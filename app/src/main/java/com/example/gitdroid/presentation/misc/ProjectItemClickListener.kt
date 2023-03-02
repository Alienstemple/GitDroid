package com.example.gitdroid.presentation.misc

import com.example.gitdroid.models.domain.Project

interface ProjectItemClickListener {
    fun onItemClicked(project: Project)
}