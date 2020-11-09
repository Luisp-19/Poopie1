package com.example.poopie1.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.poopie1.BD.NotasBD
import com.example.poopie1.BD.NotasRep
import com.example.poopie1.Notas.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//City view model
class vmodel(application: Application) : AndroidViewModel(application) {

    private val repository: NotasRep
    val allNotas: LiveData<List<Notas>>

    init {
        val daoSan = NotasBD.getDatabase(application, viewModelScope).daoSan()
        repository = NotasRep(daoSan)
        allNotas = repository.allNotas
    }

    fun insert(notas: Notas) = viewModelScope.launch{
        repository.insert(notas)
    }

    fun delete(notas: Notas) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(notas)
    }

    fun update(notas: Notas)= viewModelScope.launch(Dispatchers.IO){
        repository.update(notas)
    }
}