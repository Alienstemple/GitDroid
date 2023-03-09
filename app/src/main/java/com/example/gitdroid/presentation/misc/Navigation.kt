package com.example.gitdroid.presentation.misc

import androidx.fragment.app.Fragment
import com.example.gitdroid.presentation.vm.ProjectsViewModel

fun Fragment.navigation(): Navigation {
    return requireActivity() as Navigation
}

interface Navigation {
    fun getProjectsVm() : ProjectsViewModel
    fun openProjects()
    fun openCodeSearch()
    fun openAuth()
    fun openHello(userName: String)
}