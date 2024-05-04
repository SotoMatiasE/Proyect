package com.example.profile

import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageUri: Uri

    private var lat: Double = 0.0
    private var long: Double = 0.0

    //VALIDACION DE CAMPOS NULOS
    /*StartActivityForResul ESTA PROPERTY DA UN CODIGO(KEY)CON EL CUAL MAS ADELANTE PODRA FILTRAR LA
    RESPUESTA Y ASI SABER QUE SE TRATA DE LA MISMA ACTIVITY QUE LO LANZO*/
    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            imageUri = Uri.parse(it.data?.getStringExtra(getString(R.string.key_img)))
            val name = it.data?.getStringExtra(getString(R.string.key_name))
            val email = it.data?.getStringExtra(getString(R.string.key_email))
            val website = it.data?.getStringExtra(getString(R.string.key_website))
            val phone = it.data?.getStringExtra(getString(R.string.key_phone))
            lat = it.data?.getDoubleExtra(getString(R.string.key_latitud), 0.0) ?: 0.0
            long = it.data?.getDoubleExtra(getString(R.string.key_longitud), 0.0) ?: 0.0

            //updateUI(name!!, email!!, website!!, phone!!)
            saveUserData(name, email, website, phone)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //ESTA PROPIEDAD PERMITE GUARDAR DATOS
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        //updateUI()
        getUserData()

        setUpIntents()
    }

    //onResume NOS SIRVE AHORA PARA PODER GENERAR CAMBIOS EN TIEMPO REAL EN ESTA CASO DESHABILITA O HABILITA CLCKS
    override fun onResume() {
        super.onResume()
        val isEnable = sharedPreferences.getBoolean(getString(R.string.preference_key_enable_clicks),true)
        with(binding){
        tvName.isEnabled = isEnable
        tvEmail.isEnabled = isEnable
        tvWebsite.isEnabled = isEnable
        tvPhone.isEnabled = isEnable
        tvLocation.isEnabled = isEnable
        tvSetting.isEnabled = isEnable
        }
    }


    //CON ESTA FUNCION LE DAMOS LA PROPIEDAD DE BUSQUEDA AL TEXTVIEW
    //ES DECIR HAGO CLICK EN EL NOMBRE Y AUTOMATICAMENTE BUSCAR ESO ESCRITO EN GOOGLE
    private fun setUpIntents() {
        binding.tvName.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, binding.tvName.text)
            }
            launchIntent(intent)
        }

        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "From kotlin basic course")
                putExtra(Intent.EXTRA_TEXT, "Hi! I'm Android developer.")
            }
            launchIntent(intent)
        }

        binding.tvWebsite.setOnClickListener {
            val webPage = Uri.parse(binding.tvWebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            launchIntent(intent)
        }

        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val phone = (it as TextView).text
                data = Uri.parse("tel:$phone")
            }
            launchIntent(intent)
        }

        binding.tvLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat,$long(Cursos Android ANT)")
                `package` = "com.google.android.apps.maps"
            }
            launchIntent(intent)
        }

        binding.tvSetting.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            launchIntent(intent)
        }
    }

    //DE ESTA FORMA SI CAMBIA ALGO DE LA AP 30 EN ADELANTE ES MAS FACIL CAMBIARLO
    private fun launchIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.profile_erro_no_resolve), Toast.LENGTH_SHORT).show()
        }
        //ESTO PREGUNTA SI HAY COMPATIBILIDAD CON API SUPERIOR DEL ANDROID 11
    }

    private fun getUserData(){
        imageUri = Uri.parse(sharedPreferences.getString(getString(R.string.key_img), ""))
        val name = sharedPreferences.getString(getString(R.string.key_name), null)
        val email = sharedPreferences.getString(getString(R.string.key_email), null)
        val website = sharedPreferences.getString(getString(R.string.key_website), null)
        val phone = sharedPreferences.getString(getString(R.string.key_phone), null)
        lat = sharedPreferences.getString(getString(R.string.key_latitud), "0.0")!!.toDouble()
        long = sharedPreferences.getString(getString(R.string.key_longitud), "0.0")!!.toDouble()

        updateUI(name, email, website, phone)
    }

    private fun updateUI(name: String?, email: String?, website: String? , phone: String?) {
        with (binding) {
            imgProfile.setImageURI(imageUri)
            tvName.text = name ?: "Proyecto Kotlin PW"
            tvEmail.text = email ?: "pwkotlinsoft@gmail.com"
            tvWebsite.text = website ?: "https://puenteweb.com/pw/"
            tvPhone.text = phone ?: "+54 231321321"
        }
        //lat = -38.940576
        //long = -68.068939
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // infla el menú definido en el archivo XML menu_main y lo agrega al menú de la actividad representado por el objeto menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_edit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra(getString(R.string.key_img), imageUri.toString())
                intent.putExtra(getString(R.string.key_name), binding.tvName.text)
                intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
                intent.putExtra(getString(R.string.key_website), binding.tvWebsite.text.toString())
                intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text)
                intent.putExtra(getString(R.string.key_latitud), lat)
                intent.putExtra(getString(R.string.key_longitud), long)

                //startActivity(intent) <- solo lanzamiento
                //startActivityForResult(intent, RC_EDIT) // <- lanzamiento y espera de respuesta
                editResult.launch(intent)
            }
            //LANZAMOS LA NUEVA ACTIVIDAD "action_settings"
            R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_EDIT) {
                val name = data?.getStringExtra(getString(R.string.key_name))
                val email = data?.getStringExtra(getString(R.string.key_email))
                val website = data?.getStringExtra(getString(R.string.key_website))
                val phone = data?.getStringExtra(getString(R.string.key_phone))
                lat = data?.getDoubleExtra(getString(R.string.key_latitud), 0.0) ?: 0.0
                long = data?.getDoubleExtra(getString(R.string.key_longitud), 0.0) ?: 0.0
                imageUri = Uri.parse(data?.getStringExtra("key_image"))
                //updateUI(name, email, website, phone)

                saveUserData(name, email, website, phone)
            }
        }
    }*/

    //ASI PODEMOS ALMACENAR DATOS DE MANERA PERMANENTE
    //AHORA sharedPreferences CONTIENE LOS DATOS ALMACENADOS
    private fun saveUserData(name: String?, email: String?, website: String?, phone: String?) {
        sharedPreferences.edit {
            putString(getString(R.string.key_img), imageUri.toString())
            putString(getString(R.string.key_name), name)
            putString(getString(R.string.key_email), email)
            putString(getString(R.string.key_website), website)
            putString(getString(R.string.key_phone), phone)
            putString(getString(R.string.key_latitud), lat.toString())
            putString(getString(R.string.key_longitud), long.toString())
            apply()
        }
        updateUI(name, email, website, phone)
    }

}