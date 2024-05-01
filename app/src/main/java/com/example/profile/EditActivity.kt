package com.example.profile

import android.content.Intent
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

        //ESTA OPCION APLICA POR DEFAULT LA FLECHA SUPERIOR PARA VOLVER AL MENU ANTERIOR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            sendData()
        }else if (item.itemId == android.R.id.home) { //USANDO android.R ACCEDEMOS A LAS OPCIONES NATIVAS
            onBackPressedDispatcher.onBackPressed() //vuelvo al menu anterior con opcion nativa de android
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendData(){ //ESTA FUNCION ENVIA DATOS A MainActivity
       val intent = Intent()
        intent.putExtra(getString(R.string.key_name), binding.etName.text.toString())
        intent.putExtra(getString(R.string.key_email), binding.etEmail.text.toString())
        intent.putExtra(getString(R.string.key_website), binding.etWebSite.text.toString())
        intent.putExtra(getString(R.string.key_phone), binding.etPhone.text.toString())
        intent.putExtra(getString(R.string.key_latitud), binding.etLat.text.toString().toDouble())
        intent.putExtra(getString(R.string.key_longitud), binding.etLong.text.toString().toDouble())
        setResult(RESULT_OK, intent)
        finish()
    }
}