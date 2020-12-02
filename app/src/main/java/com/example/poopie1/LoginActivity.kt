package com.example.poopie1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.poopie1.api.Builder
import com.example.poopie1.api.EndPoints
import com.example.poopie1.api.LoginOutput
import com.example.poopie1.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

//        <meta-data android:name="com.google.android.geo.API_KEY" /> manifest
class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText:EditText
    private lateinit var passEditText:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText=findViewById(R.id.username)
        passEditText=findViewById(R.id.password)

        //Para dados de acesso rápido
        val sharedPref: SharedPreferences = getSharedPreferences( //Abre o ficheiro SharedPreferences
            getString(R.string.preference_file_key), Context.MODE_PRIVATE )

        //Verifica se tem login automatico
        val automatic_login_check = sharedPref.getBoolean(getString(R.string.automatic_login_check), false)
        Log.d("SP_AutoLoginCheck", "$automatic_login_check")

        //Se tiver, entra logo no mapa
        if( automatic_login_check ) {
            val intent = Intent(this@LoginActivity, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

        fun login(view: View) {
            // mente na var username o text escrito
            val username=usernameEditText.text
            val pass=passEditText.text

            //Chama o serviceBuilder
            val request = Builder.buildService(EndPoints::class.java)
            val call = request.postTest(username.toString(), pass.toString().sha256())

            //Resposta do Post feito para o login
            call.enqueue(object : Callback<LoginOutput>{
                override fun onResponse(call: Call<LoginOutput>, response: Response<LoginOutput>) {
                if (response.isSuccessful){ //Se funcionar

                    val resp = response.body()!!
                    if (resp.success){  //Muda para o mapa google

                        //Abrir a sharedPreferences para poder adicionar os dados do login
                        val sharedPref: SharedPreferences = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE ) //Ficheiro princiapl que guarda os dados do login
                        with ( sharedPref.edit() ) {
                            putBoolean(getString(R.string.automatic_login_check), true) //Guarda para no create verifivcr
                            putString(getString(R.string.automatic_login_username), username.toString())
                            commit()
                        }

                        val intent = Intent(this@LoginActivity, MapsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {    //Caso não funciona manda um toast
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
                override fun onFailure(call: Call<LoginOutput>, t: Throwable) { //Caso haja um erro da api
                    Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        //Método de encriptação
        private fun hashString(input: String, algorithm: String): String {
            return MessageDigest
                    .getInstance(algorithm)
                    .digest(input.toByteArray())
                    .fold("", {str, it -> str + "%02x".format(it) })
        }

        //Encriptar a palavra pass
        fun String.sha256(): String {
            return hashString(this,"SHA-256")
        }

        //Codificação do butão para mostrar as notas
        fun show_notas(view: View) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
//}

