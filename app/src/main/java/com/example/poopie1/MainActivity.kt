package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

private lateinit var notasViewModel: vmodel

class MainActivity : AppCompatActivity(), NotasAdapt.OnItemClickListener {
    private val AddNotasrq = 1
    private val EditNoteRq = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotasAdapt(this, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notasViewModel = ViewModelProvider(this).get(vmodel::class.java)
        notasViewModel.allNotas.observe(this, { notas ->
            notas?.let { adapter.setNotas(it) }
        })

          val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, AddNotas::class.java)
            startActivityForResult(intent, AddNotasrq)
        }

        //Apagar notas, com swipe para a direita
        val ItemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notasViewModel.delete( adapter.getAt(viewHolder.adapterPosition))
            }
        }

        val itemTouchHelper = ItemTouchHelper( ItemTouchHelperCallback )
        itemTouchHelper.attachToRecyclerView( recyclerView )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddNotasrq && resultCode == Activity.RESULT_OK) {
            val notad = data?.getStringExtra(AddNotas.EXTRA_REPLY).toString()
            val subnotad = data?.getStringExtra(AddNotas.EXTRA1_REPLY).toString()

                    val notaa = Notas(nota = (notad), subnota = (subnotad))

                    notasViewModel.insert(notaa)
        }
        else if (requestCode == EditNoteRq && resultCode == Activity.RESULT_OK){

            val id = data?.getIntExtra(EditNotas.EXTRAid_REPLY, -1)
            if(id == 1){
                Toast.makeText(this@MainActivity, "Não deu bruv, tenta outra vez", Toast.LENGTH_SHORT).show()
                return
            }

            val notad = data?.getStringExtra(EditNotas.EXTRAs_REPLY).toString()
            val subnotad = data?.getStringExtra(EditNotas.EXTRAss_REPLY).toString()
            val notaa = Notas(id, notad, subnotad)

            notasViewModel.update(notaa)
        }
        else {
            Toast.makeText(
                applicationContext,
                "Welcome to the main pagez",
                Toast.LENGTH_LONG).show()
        }
    }

    //Para editar as notas
    override fun onItemClicked (notas: Notas){
        val intent = Intent (this, EditNotas::class.java).apply {
            intent.putExtra(EditNotas.EXTRAid_REPLY, notas.id)
            intent.putExtra(EditNotas.EXTRAs_REPLY,  notas.nota)
            intent.putExtra(EditNotas.EXTRAss_REPLY, notas.subnota)
        }
        Log.d("test", "${notas.id}")
        startActivityForResult(intent, EditNoteRq)
    }

}
