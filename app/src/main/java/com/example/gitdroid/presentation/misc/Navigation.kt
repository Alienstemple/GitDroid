package com.example.gitdroid.presentation.misc

import androidx.fragment.app.Fragment

fun Fragment.navigation(): Navigation {
    return requireActivity() as Navigation
}

interface Navigation {
    fun openProjects()
    fun openCodeSearch()
    fun openAuth()
    fun openHello(userName: String, avatarUrl: String)
    fun openFindReposByUser()
}