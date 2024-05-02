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

        //VALIDACION DE CAMPOS NULOS
        intent.extras?.let { //SI ESTRAS LLEGA A SER NULL JAMAS SE EJECUTA OEL CODIGO Y VERIFICA UNA SOLA VEZ
            binding.etName.setText(it.getString(getString(R.string.key_name)))
            binding.etEmail.setText(it.getString(getString(R.string.key_email)))
            binding.etWebSite.setText(it?.getString(getString(R.string.key_website)))
            binding.etPhone.setText(it.getString(getString(R.string.key_phone)))
            binding.etLat.setText(it.getDouble(getString(R.string.key_latitud)).toString())
            binding.etLong.setText(it.getDouble(getString(R.string.key_longitud)).toString())
        }

        binding.etWebSite.setOnFocusChangeListener { v, isFocus ->
            if (isFocus) {
                binding.etWebSite.text?.let{ //CON it QUE ES EL EDITABLE DE text QUE MUESTRE EL CURSOR DEPENDIENDO LA LONGITUD DEL TEXT
                    binding.etWebSite.setSelection(it.length)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.action_save -> sendData()
            //HACER ESTO ES LO MISMO Y MEJOR QUE EL if QUE ESTA ABAJO
        }

        /*if (item.itemId == R.id.action_save){
            sendData()
        }else if (item.itemId == android.R.id.home) { //USANDO android.R ACCEDEMOS A LAS OPCIONES NATIVAS
            onBackPressedDispatcher.onBackPressed() //vuelvo al menu anterior con fleacha opcion nativa de android
        }*/
        return super.onOptionsItemSelected(item)
    }

    fun sendData(){ //ESTA FUNCION ENVIA DATOS A MainActivity
       val intent = Intent()

        //VALIDACION DE CAMPOS
        with(binding){
            intent.apply {
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email), etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebSite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_latitud), etLat.text.toString().toDouble())
                putExtra(getString(R.string.key_longitud), etLong.text.toString().toDouble())
                //DE ESTA MANERA AHORRAMOS REPETIR INTENTS Y BINDINGS ESTO ES MAS LIMPIO
            }
        }

        /*intent.putExtra(getString(R.string.key_name), binding.etName.text.toString())
        intent.putExtra(getString(R.string.key_email), binding.etEmail.text.toString())
        intent.putExtra(getString(R.string.key_website), binding.etWebSite.text.toString())
        intent.putExtra(getString(R.string.key_phone), binding.etPhone.text.toString())
        intent.putExtra(getString(R.string.key_latitud), binding.etLat.text.toString().toDouble())
        intent.putExtra(getString(R.string.key_longitud), binding.etLong.text.toString().toDouble())*/
        setResult(RESULT_OK, intent)
        finish()
    }
}