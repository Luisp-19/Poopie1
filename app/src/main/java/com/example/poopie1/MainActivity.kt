package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poopie1.Notas.Notas
import com.example.poopie1.adaptER.NotasAdapt
import com.example.poopie1.vmodel.vmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var citiesViewModel: vmodel

class MainActivity : AppCompatActivity() {
    private val AddCitiesrq = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotasAdapt(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        citiesViewModel = ViewModelProvider(this).get(vmodel::class.java)
        citiesViewModel.allNotas.observe(this, { citites ->
            citites?.let { adapter.setCities(it) }
        })

          val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, AddNotas::class.java)
            startActivityForResult(intent, AddCitiesrq)
        }

        //Apagar notas, com swipe

        val ItemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                citiesViewModel.delete( adapter.getAt(viewHolder.adapterPosition))
            }
        }

        val itemTouchHelper = ItemTouchHelper( ItemTouchHelperCallback )
        itemTouchHelper.attachToRecyclerView( recyclerView )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddCitiesrq && resultCode == Activity.RESULT_OK) {
            val notad = data?.getStringExtra(AddNotas.EXTRA_REPLY).toString()
            val subnotad = data?.getStringExtra(AddNotas.EXTRA1_REPLY).toString()

                    val cities = Notas(nota = (notad), subnota = (subnotad))

                    citiesViewModel.insert(cities)
        } else {
            Toast.makeText(
                applicationContext,
                "Welcome to the main pagez",
                Toast.LENGTH_LONG).show()
        }
    }
}
