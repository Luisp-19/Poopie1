package com.example.poopie1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poopie1.Cities.Cities
import com.example.poopie1.adaptER.CitiesAdapt
import com.example.poopie1.vmodel.vmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var citiesViewModel: vmodel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CitiesAdapt(this)
        recyclerView.adapter = adapter
        recyclerView.LayoutManager = LinearLayoutManager(this)

        citiesViewModel = ViewModelProvider(this).get(vmodel::class.java)
        citiesViewModel.allCities.observe(this, { citites ->
            citites?.let { adapter.setCities(it) }
        })

          val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, AddCities::class.java)
            startActivityForResult(intent, AddCitiesrq)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddCitiesrq && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddCities.EXTRA_REPLY)?.let {
                val name = it
                data?.getStringExtra(AddCities.EXTRA1_REPLY)?.let {
                    val cities = Cities(city = (city), country = (cnt))
                    vmodel.insert(cities)
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                "INSERE DUMA VEZ",
                Toast.LENGTH_LONG).show()
        }
    }

}
