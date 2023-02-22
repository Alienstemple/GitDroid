package com.example.gitdroid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.gitdroid.R
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.databinding.ActivityStartAppBinding
import com.example.gitdroid.presentation.fragments.FindReposByUserFragment
import com.example.gitdroid.presentation.fragments.ListIssuesFragment

class StartAppActivity : AppCompatActivity(), Navigation {
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
//        launchFragment(FindReposByUserFragment.newInstance())
        launchFragment(ListIssuesFragment.newInstance())
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d(TAG, "Transact with name ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_repos_by_user_frag_container, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val TAG = "StartAppLog"
    }
}