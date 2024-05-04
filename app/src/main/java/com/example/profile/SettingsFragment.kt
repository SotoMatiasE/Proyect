package com.example.profile

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat


//funciona como actividad donde se vincula una vista NO PUEDE EXISTIR POR SI SOLO SI NO QUE TIENE
// QUE ALOJARSE EN OTRA ACTIVIDAD EN ESTE CASO SettingsActivity
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey) //VINCULADO A LA VISTA
    }
}