package com.example.gitdroid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.gitdroid.GitDroidApplication
import com.example.gitdroid.databinding.ActivityAuthBinding
import com.example.gitdroid.databinding.ActivityMainBinding
import com.example.gitdroid.domain.AuthRepositoryImpl
import com.example.gitdroid.presentation.vm.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by viewModels {
        (application as GitDroidApplication).appComponent.authViewModelFactory()
    }

    fun AuthRepositoryImpl.startActivityForSignInWithProvider(activity: FragmentActivity) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (authViewModel.checkAuthorized()) {
           // open MainActivity
        } else {
            // binding сюда перенесем
        }
    }

    companion object {
        const val TAG = "AuthActLog"
    }
}