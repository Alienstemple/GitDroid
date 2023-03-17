package com.example.gitdroid.presentation.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.gitdroid.R

/**
 * Фрагмент настроек
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}