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

class StartAppActivity : AppCompatActivity(), RepositoryItemClickListener {
    private lateinit var startAppActivityBinding: ActivityStartAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAppActivityBinding = ActivityStartAppBinding.inflate(layoutInflater)
        setContentView(startAppActivityBinding.root)

        startAppActivityBinding.findAllReposByUsernameBtn.setOnClickListener {
//            openFindReposByUser()
        }
    }

    override fun onItemClicked(ghRepository: GHRepository) {
//        launchFragment(ListIssuesFragment.newInstance(ghRepository.owner.login, ghRepository.name))
    }

    companion object {
        const val TAG = "StartAppLog"
    }
}