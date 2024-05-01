package com.example.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        updateUI()

        binding.tvLocation.setOnClickListener {
            binding.tvLocation.text = "Lat: $lat, Long: $long"
        }


    }

    private fun updateUI(name: String = "Proyecto Kotlin PW", email: String = "pwkotlinsoft@gmail.com",
                         website: String = "pwsoft.com", phone: String = "+54 231321321") {

        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
        lat = -38.940576
        long = -68.068939
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // infla el menú definido en el archivo XML menu_main y lo agrega al menú de la actividad representado por el objeto menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit){
            startActivity(Intent(this, EditActivity::class.java)) //DE ESTA MANERA LLAMAMOS A EditActivity similar a llamar a otro formulario
        }
        return super.onOptionsItemSelected(item)
    }
}