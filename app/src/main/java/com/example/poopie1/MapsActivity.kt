package com.example.poopie1

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.poopie1.api.*
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var username: String

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val ADD_MARKER_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val sharedPref: SharedPreferences = getSharedPreferences( //Abre o ficheiro SharedPreferences
            getString(R.string.preference_file_key), Context.MODE_PRIVATE )
        username = sharedPref.getString(getString(R.string.automatic_login_username), null)!!

        val request = Builder.buildService(EndPoints::class.java)
        val call = request.getCoordenadas()
        var position: LatLng

        //Resposta do Post feito para o login
        call.enqueue(object : Callback<List<mapa>> {
            override fun onResponse(call: Call<List<mapa>>, response: Response<List<mapa>>) {
                if (response.isSuccessful){ //Se funcionar
                    val resp = response.body()!!

                    for( marker in resp ) {
                        position = LatLng( marker.latitude.toDouble(), marker.longitude.toDouble() )
                        mMap.addMarker(MarkerOptions().position(position).title(marker.problema))
                    }
                }
            }
            override fun onFailure(call: Call<List<mapa>>, t: Throwable) { //Caso haja um erro da api
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    lastLocation = location
                    var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                    Log.d(
                        "****LOCATION****",
                        "new location received - " + loc.latitude + " - " + loc.longitude
                    )
                }
            }
        }

        getLocationUpdates()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( data != null && requestCode == ADD_MARKER_REQUEST_CODE && resultCode == Activity.RESULT_OK ) {
            val problema = data.getStringExtra( AddMarkerActivity.EXTRA_PROBLEMA ).toString()
            val tipo = data.getStringExtra( AddMarkerActivity.EXTRA_TIPO_PROBLEMA ).toString()
            val latitude = data.getStringExtra( AddMarkerActivity.EXTRA_LATITUDE ).toString()
            val longitude = data.getStringExtra( AddMarkerActivity.EXTRA_LONGITUDE ).toString()
            val username = data.getStringExtra( AddMarkerActivity.EXTRA_USERNAME ).toString()
            val position = LatLng( latitude.toDouble(), longitude.toDouble() )

            val request = Builder.buildService(EndPoints::class.java)
            val call = request.createMarker(
                problema,
                tipo,
                latitude,
                longitude,
                username
            )

            //Resposta do Post feito para o criar marcador
            call.enqueue(object : Callback<NoteOutput> {
                override fun onResponse(call: Call<NoteOutput>, response: Response<NoteOutput>) {
                    if (response.isSuccessful){ //Se funcionar
                        val resp = response.body()!!

                        if( resp.success ) {
                            mMap.addMarker(MarkerOptions().position(position).title(problema))
                        }
                    }
                }
                override fun onFailure(call: Call<NoteOutput>, t: Throwable) { // Caso haja um erro da api
                    Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Cria o menu já prefeito
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_opcoes, menu)
        return true
    }

    //Executa as opções selecionadas
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_marker -> {
                val intent = Intent(this@MapsActivity, AddMarkerActivity::class.java)
                intent.putExtra(AddMarkerActivity.EXTRA_LATITUDE, lastLocation.latitude.toString())
                intent.putExtra(AddMarkerActivity.EXTRA_LONGITUDE, lastLocation.longitude.toString())
                intent.putExtra(AddMarkerActivity.EXTRA_USERNAME, username)
                startActivityForResult(intent, ADD_MARKER_REQUEST_CODE)
                true
            }

            R.id.logout_btn -> {

                //Abre as SharedPreferences em modo editar
                val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE
                )
                with(sharedPref.edit()) {
                    putBoolean(getString(R.string.automatic_login_check), false) //Desativa o login automatico
                    putString(getString(R.string.automatic_login_username), null) //Remove os dados do username, para null
                    commit()
                }

                val intent = Intent(this@MapsActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getLocationUpdates()
    {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 5000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    lastLocation = locationResult.lastLocation
                    var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                    Log.d(
                        "****LOCATION****",
                        "New Location Received - " + loc.latitude + " -" + loc.longitude
                    )
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
        Log.d("****LOCATION****", "onPause - removeLocationUpdates")
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("****LOCATION****", "onResume - startLocationUpdates")
    }
}