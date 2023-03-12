package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gitdroid.data.auth.SessionManager
import com.example.gitdroid.databinding.FragmentHelloBinding
import com.example.gitdroid.presentation.MainActivity
import com.example.gitdroid.presentation.misc.navigation
import com.google.firebase.auth.FirebaseAuth

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHelloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            navigation().openCodeSearch()
        }

        binding.myProjBtn.setOnClickListener {
            navigation().openProjects()
        }

        binding.logoutBtn.setOnClickListener {
            navigation().logout()
        }
    }

    companion object {
        const val TAG = "HelloFragmLog"
        @JvmStatic
        fun newInstance() =
            HelloFragment()
    }
}