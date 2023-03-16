package com.example.gitdroid

import android.app.Application
import com.example.gitdroid.di.AppComponent
import com.example.gitdroid.di.DaggerAppComponent

class GitDroidApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}