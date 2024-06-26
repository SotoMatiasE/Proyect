package com.example.profile

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat


//funciona como actividad donde se vincula una vista NO PUEDE EXISTIR POR SI SOLO SI NO QUE TIENE
// QUE ALOJARSE EN OTRA ACTIVIDAD EN ESTE CASO SettingsActivity
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey) //VINCULADO A LA VISTA

        /*        ESTE BLOQUE DE CODIGO LE DICE AL BOTON DE "ELIMINAR DATOS DEL USUARIO" QUE LOS RESTABLEZCA
        Y AL SALIR DE LA APP Y VOLVER A ENTRAR LOS DATOS ALTERADOS SON NUEVAMENTE LOS PREDETERMINADOS*/
        val deleteUserDataPreferenceFragment =
            findPreference<Preference>(getString(R.string.preference_key_delete_data))
        deleteUserDataPreferenceFragment?.setOnPreferenceClickListener {
            val sharePreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharePreferences.edit {
                putString(getString(R.string.key_img), null)
                putString(getString(R.string.key_name), null)
                putString(getString(R.string.key_email), null)
                putString(getString(R.string.key_website), null)
                putString(getString(R.string.key_phone), null)
                putString(getString(R.string.key_latitud), null)
                putString(getString(R.string.key_longitud), null)
                apply()
            }
            true
        }

        //REFERENCIA A ELEMENTOS GRAFICOS
        val switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(getString(R.string.preference_key_enable_clicks))
        val listPreferenceCompat = findPreference<ListPreference>(getString(R.string.preference_key_ui_img_size))


        /*ELIMINA POR COMPLETO TODAS LAS COFIGURACIONES Y DATOS EDITADOS */
        val restoreAllPreference =
            findPreference<Preference>(getString(R.string.preference_key_restore_data))
        restoreAllPreference?.setOnPreferenceClickListener {
            val sharePreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharePreferences.edit().clear().apply() /*ESTE METODO .edit().clear().apply() LIMPIA POR COMPLETO LAS PREFERENCIASDATOS Y CONFIGURACIONES*/

            //RESTAURA GRAFICAMENTE
            switchPreferenceCompat?.isChecked = true
            listPreferenceCompat?.value = getString(R.string.preference_key_img_large)
            true
        }

        //NO AFECTA A DATOS DEL USUARIO SOLO LAS CONFIGURACIONES, TAMANIO IMG, CLICKS, ETC..
        val restoreSettingsPreference = findPreference<Preference>(getString(R.string.preference_key_restore_settings))
        restoreSettingsPreference?.setOnPreferenceClickListener {
            switchPreferenceCompat?.isChecked = true
            listPreferenceCompat?.value = getString(R.string.preference_key_img_large)

            true
        }
    }
}




























