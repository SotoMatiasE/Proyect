package com.example.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)


        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.settings_title)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, SettingsFragment()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){ //para que pueda retroiceder
            onBackPressedDispatcher.onBackPressed()//flecha de volver
        }
        return super.onOptionsItemSelected(item)
    }
}





















