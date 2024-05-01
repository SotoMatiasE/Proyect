package com.example.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.profile.databinding.ActivityEditBinding
import com.example.profile.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.etName.setText(intent.extras?.getString(getString(R.string.key_name)))
        binding.etEmail.setText(intent.extras?.getString(getString(R.string.key_email)))
        binding.etWebSite.setText(intent.extras?.getString(getString(R.string.key_website)))
        binding.etPhone.setText(intent.extras?.getString(getString(R.string.key_phone)))
        binding.etLat.setText(intent.extras?.getDouble(getString(R.string.key_latitud)).toString())
        binding.etLong.setText(intent.extras?.getDouble(getString(R.string.key_longitud)).toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}