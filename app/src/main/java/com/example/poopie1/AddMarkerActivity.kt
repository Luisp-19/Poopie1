package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.poopie1.api.Builder
import com.example.poopie1.api.EndPoints
import com.example.poopie1.api.NoteOutput
import com.example.poopie1.api.mapa
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMarkerActivity : AppCompatActivity() {
    private lateinit var problema: EditText
    private lateinit var tipo: EditText
    private lateinit var coordenadas: TextView
    private lateinit var lastLocation: LatLng
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)

        problema = findViewById<EditText>( R.id.problema )
        tipo = findViewById<EditText>( R.id.tipo )
        coordenadas = findViewById<TextView>( R.id.coordenadas )

        val intent = intent
        val latitude = intent.getStringExtra( EXTRA_LATITUDE ).toString()
        val longitude = intent.getStringExtra( EXTRA_LONGITUDE ).toString()
        username = intent.getStringExtra( EXTRA_USERNAME ).toString()

        lastLocation = LatLng(latitude.toDouble(), longitude.toDouble())
        coordenadas.text = "$latitude, $longitude"
    }

    fun submit( view: View) {
        val replyIntent = Intent()

        replyIntent.putExtra( EXTRA_PROBLEMA, problema.text.toString())
        replyIntent.putExtra( EXTRA_TIPO_PROBLEMA, tipo.text.toString())
        replyIntent.putExtra( EXTRA_LATITUDE, lastLocation.latitude.toString())
        replyIntent.putExtra( EXTRA_LONGITUDE, lastLocation.longitude.toString())
        replyIntent.putExtra( EXTRA_USERNAME, username )
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    companion object {
        const val EXTRA_TIPO_PROBLEMA = "estg.ipvc.pm_app.activity.addmarkeractivity.EXTRA_TIPO_PROBLEMA"
        const val EXTRA_PROBLEMA = "estg.ipvc.pm_app.activity.addmarkeractivity.EXTRA_PROBLEMA"
        const val EXTRA_LATITUDE = "estg.ipvc.pm_app.activity.addmarkeractivity.EXTRA_LATITUDE"
        const val EXTRA_LONGITUDE = "estg.ipvc.pm_app.activity.addmarkeractivity.EXTRA_LONGITUDE"
        const val EXTRA_USERNAME = "estg.ipvc.pm_app.activity.addmarkeractivity.EXTRA_USERNAME"
    }
}