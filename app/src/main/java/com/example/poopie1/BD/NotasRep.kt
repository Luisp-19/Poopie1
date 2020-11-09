package com.example.poopie1.BD

import androidx.lifecycle.LiveData
import com.example.poopie1.Notas.Notas
import com.example.poopie1.dao.DaoSan

class NotasRep(private val daoSan: DaoSan) {

    val allNotas: LiveData<List<Notas>> = daoSan.getAlphabetizedCities()

    suspend fun insert(notas: Notas){
        daoSan.insert(notas)
    }

    suspend fun delete(notas: Notas){
        daoSan.delete(notas)
    }

    suspend fun update(notas: Notas) {
        daoSan.update(notas)
    }
}