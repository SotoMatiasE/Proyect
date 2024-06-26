package com.example.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.profile.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private var imageUri: Uri? = null


    private val galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            imageUri = it.data?.data

            //validacion y otorgamiento de permisos para uri y persista la img de la galeria
            imageUri?.let {
                val contentResolver = applicationContext.contentResolver
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

                contentResolver.takePersistableUriPermission(it, takeFlags)
                updateImg()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //ESTA OPCION APLICA POR DEFAULT LA FLECHA SUPERIOR PARA VOLVER AL MENU ANTERIOR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            //VALIDACION DE CAMPOS NULOS
            intent.extras?.let { //SI EXTRAS LLEGA A SER NULL JAMAS SE EJECUTA OEL CODIGO Y VERIFICA UNA SOLA VEZ
                etName.setText(it.getString(getString(R.string.key_name)))
                etEmail.setText(it.getString(getString(R.string.key_email)))
                etWebSite.setText(it.getString(getString(R.string.key_website)))
                etPhone.setText(it.getString(getString(R.string.key_phone)))
                etLat.setText(it.getDouble(getString(R.string.key_latitud)).toString())
                etLong.setText(it.getDouble(getString(R.string.key_longitud)).toString())
                imageUri = Uri.parse(it.getString(getString(R.string.key_img)))
                updateImg()
            }
            //CON it QUE ES EL EDITABLE DE text QUE MUESTRE EL CURSOR DEPENDIENDO LA LONGITUD DEL TEXT
            etEmail.setOnFocusChangeListener { v, isFocused ->
                if (isFocused) {
                    etEmail.text?.let { etEmail.setSelection(it.length) }
                }
                etWebSite.setOnFocusChangeListener { v, isFocused ->
                    if (isFocused) {
                        etWebSite.text?.let {etWebSite.setSelection(it.length)
                        }
                    }
                }
                etPhone.setOnFocusChangeListener { v, isFocused ->
                    if (isFocused) {
                        etPhone.text?.let {etPhone.setSelection(it.length)
                        }
                    }
                }
                etLat.setOnFocusChangeListener { v, isFocused ->
                    if (isFocused) {
                        etLat.text?.let {etLat.setSelection(it.length)
                        }
                    }
                }
                etLong.setOnFocusChangeListener { v, isFocused ->
                    if (isFocused) {
                        etLong.text?.let {etLong.setSelection(it.length)
                        }
                    }
                }
            }
            //ENTRA A LA GALERIA PARA ABRIR UN DOC
            btnSelectPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/jpeg"
                }
               /* startActivityForResult(intent, RC_GALERY)*/
                galleryResult.launch(intent)
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

    private fun updateImg(){
        binding.imgProfile.setImageURI(imageUri)
    }

    private fun sendData(){ //ESTA FUNCION ENVIA DATOS A MainActivity
       val intent = Intent()

        //VALIDACION DE CAMPOS
        with(binding){
            intent.apply {
                putExtra(getString(R.string.key_img), imageUri.toString())
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email), etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebSite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_latitud), etLat.text.toString().toDouble())
                putExtra(getString(R.string.key_longitud), etLong.text.toString().toDouble())
                //DE ESTA MANERA AHORRAMOS REPETIR INTENTS Y BINDINGS ESTO ES MAS LIMPIO
            }
        }
        setResult(RESULT_OK, intent)
        finish()
    }

}