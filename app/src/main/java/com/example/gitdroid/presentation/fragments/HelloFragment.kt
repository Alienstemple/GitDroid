package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gitdroid.R
import com.example.gitdroid.databinding.FragmentHelloBinding
import com.example.gitdroid.databinding.FragmentListIssuesBinding
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.misc.navigation
import com.google.firebase.auth.FirebaseAuth

private const val ARG_USERNAME = "userName"

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding

    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHelloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Username in HelloFrag = $userName")
        binding.userNameTv.text = userName   // FIXME userName is null

        binding.searchBtn.setOnClickListener {
            navigation().openCodeSearch()
        }

        binding.myProjBtn.setOnClickListener {
            navigation().openProjects()
        }

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // TODO session manager - remove access token from SharedPrefs
            Log.d(MainActivity.TAG, "Logout success")
            navigation().openAuth()
        }
    }

    companion object {
        const val TAG = "HelloFragmLog"
        @JvmStatic
        fun newInstance(userName: String) =
            HelloFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, userName)
                }
            }
    }
}