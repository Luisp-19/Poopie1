package com.example.poopie1.BD

import androidx.lifecycle.LiveData
import com.example.poopie1.Notas.Notas
import com.example.poopie1.dao.DaoSan

class NotasRep(private val daoSan: DaoSan) {

    val allNotas: LiveData<List<Notas>> = daoSan.getAlphabetizedCities()

    suspend fun insert(city: Notas){
        daoSan.insert(city)
    }

    suspend fun delete(city: Notas){
        daoSan.delete(city)
    }
}