package com.example.gitdroid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityStartAppBinding
import com.example.gitdroid.models.domain.GHRepository
import com.example.gitdroid.presentation.fragments.FindReposByUserFragment
import com.example.gitdroid.presentation.fragments.ListIssuesFragment
import com.example.gitdroid.presentation.misc.Navigation
import com.example.gitdroid.presentation.misc.RepositoryItemClickListener

class StartAppActivity : AppCompatActivity(), Navigation, RepositoryItemClickListener {
    private lateinit var startAppActivityBinding: ActivityStartAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAppActivityBinding = ActivityStartAppBinding.inflate(layoutInflater)
        setContentView(startAppActivityBinding.root)

        startAppActivityBinding.findAllReposByUsernameBtn.setOnClickListener {
            openFindReposByUser()
        }
    }

    override fun openFindReposByUser() {
        launchFragment(FindReposByUserFragment.newInstance())
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_repos_by_user_frag_container, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    override fun onItemClicked(ghRepository: GHRepository) {
        launchFragment(ListIssuesFragment.newInstance()) // TODO pass ghRepository
    }

    companion object {
        const val TAG = "StartAppLog"
    }
}