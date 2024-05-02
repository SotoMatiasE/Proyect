package com.example.profile

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
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

        setUpIntents()
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
                //PRIMERO LE INDICAMOS QUE SEA PARA ENVIO EMAILS
                data = Uri.parse("mailto:") //CON ESTO YA SABE QUE ES UN CORREO

                //CONFIG DE LOS ARGUMENTOS
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "Enviado desde Kotlin") //Asunto del correo
                putExtra(Intent.EXTRA_TEXT, "Texto de prueba")//Mensaje que se envia
            }
            launchIntent(intent)
        }

        binding.tvWebsite.setOnClickListener {
            val webPage = Uri.parse(binding.tvWebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            launchIntent(intent)
        }

        //MARCA EL NUMERO PARA QUE SOLO LLAMES
        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val phone = (it as TextView).text
                data = Uri.parse("tel:$phone")
            }
            launchIntent(intent)
        }

        binding.tvLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat, $long(Puente Web)")
                `package` = "com.google.android.apps.maps"
            }
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


    private fun updateUI(name: String = "Proyecto Kotlin PW", email: String = "pwkotlinsoft@gmail.com",
                         website: String = "https://puenteweb.com/pw/", phone: String = "+54 231321321") {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
        //lat = -38.940576
        //long = -68.068939
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // infla el menú definido en el archivo XML menu_main y lo agrega al menú de la actividad representado por el objeto menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit){
            val intent =Intent(this, EditActivity::class.java)
            //COMO PASAMOS DATOS LA ACTIVITY PRINCIPAL A LA ACTIVITY EDIT
            intent.putExtra(getString(R.string.key_name), binding.tvName.text)
            intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
            intent.putExtra(getString(R.string.key_website), binding.tvWebsite.text.toString())
            intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text)
            intent.putExtra(getString(R.string.key_latitud), lat)
            intent.putExtra(getString(R.string.key_longitud), long)
            //TODOS ESTOS DATOS SE ESTAN ENVIANDO A EditActivity

            //startActivity(intent) //DE ESTA MANERA LLAMAMOS A EditActivity similar a llamar a otro formulario

            /*startActivityForResult(intent, RC_EDIT)// LANZAMIENTO Y ESPERA DE LA RESPUESTA*/

            editResult.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    //VALIDACION DE CAMPOS NULOS
    /*StartActivityForResul ESTA PROPERTY DA UN CODIGO(KEY)CON EL CUAL MAS ADELANTE PODRA FILTRAR LA
    RESPUESTA Y ASI SABER QUE SE TRATA DE LA MISMA ACTIVITY QUE LO LANZO*/
    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            val name = it.data?.getStringExtra(getString(R.string.key_name))
            val email = it.data?.getStringExtra(getString(R.string.key_email))
            val website = it.data?.getStringExtra(getString(R.string.key_website))
            val phone = it.data?.getStringExtra(getString(R.string.key_phone))
            lat = it.data?.getDoubleExtra(getString(R.string.key_latitud), 0.0) ?: 0.0
            long = it.data?.getDoubleExtra(getString(R.string.key_longitud), 0.0) ?: 0.0

            updateUI(name!!, email!!, website!!, phone!!)
        }

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

                updateUI(name!!, email!!, website!!, phone!!)
            }
        }
    }*/

    companion object { //CONSTANTE CREADA
        private const val RC_EDIT = 21 //ACTUALMENTE NO ESTA SIENDO USADA
    }


}