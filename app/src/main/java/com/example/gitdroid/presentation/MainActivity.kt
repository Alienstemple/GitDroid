package com.example.gitdroid.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gitdroid.R
import com.example.gitdroid.data.ProjectsFirebaseRepositoryImpl
import com.example.gitdroid.data.room.ProjectDatabase
import com.example.gitdroid.data.room.ProjectsRoomRepository
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.domain.ProjectsFirebaseRepository
import com.example.gitdroid.domain.ProjectsInteractor
import com.example.gitdroid.domain.ProjectsInteractorImpl
import com.example.gitdroid.presentation.fragments.*
import com.example.gitdroid.presentation.misc.Navigation
import com.example.gitdroid.presentation.vm.ProjectsViewModel
import com.example.gitdroid.presentation.vm.ProjectsViewModelFactory


class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private var projectsFirebaseRepository: ProjectsFirebaseRepository? = null
    private var projectsRoomRepository: ProjectsRoomRepository? = null
    private var projectsInteractor: ProjectsInteractor? = null
    private var projectsViewModel: ProjectsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initNavDrawer()

        openAuth()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent() called with: intent = $intent")
        // Only if authorized
        // if Bundle ret true
        if (intent?.extras?.getBoolean("IS_AUTHORIZED") == true) {
            Log.d(TAG, "IS_AUTHORIZED = true")
            initViewModel()
        }
    }

    private fun initViewModel() {
        val dao = ProjectDatabase.getDatabaseClient(this).projectDao()
        projectsFirebaseRepository = ProjectsFirebaseRepositoryImpl()
        projectsRoomRepository = ProjectsRoomRepository(dao)
        projectsInteractor =
            ProjectsInteractorImpl(projectsFirebaseRepository as ProjectsFirebaseRepository,
                projectsRoomRepository as ProjectsRoomRepository)

        projectsViewModel =
            ViewModelProvider(this,
                ProjectsViewModelFactory(projectsInteractor as ProjectsInteractorImpl))[ProjectsViewModel::class.java]

    }

    private fun initNavDrawer() = with(mainBinding) {
        // Action Drawer Menu
        toggle = ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,
            R.string.open_nav_drawer,
            R.string.close_nav_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.searchItem -> {
                    Log.d(TAG, "Item 1 selected")
                    openCodeSearch()
                }
                R.id.myProjItem -> {
                    Log.d(TAG, "Item 2 selected")
                    openProjects()
                }
                R.id.repoAndIssItem -> {
                    Log.d(TAG, "Item 3 selected")
                }
                R.id.settingsItem -> {
                    Log.d(TAG, "Item 4 selected")
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getProjectsVm(): ProjectsViewModel = projectsViewModel as ProjectsViewModel

    override fun openProjects() {
        launchFragment(ProjectsFragment.newInstance())
    }

    override fun openCodeSearch() {
        launchFragment(CodeSearchFragment.newInstance())
    }

    override fun openAuth() {
        launchFragment(AuthFragment.newInstance())
    }

    override fun openHello(userName: String, avatarUrl: String) {
        launchFragment(HelloFragment.newInstance(userName, avatarUrl))
    }

    override fun openFindReposByUser() {
        launchFragment(FindReposByUserFragment.newInstance())
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(StartAppActivity.TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActFragmContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val TAG = "MainActLog"
    }
}