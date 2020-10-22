package com.example.poopie1.BD

import androidx.lifecycle.LiveData
import com.example.poopie1.Cities.Cities
import com.example.poopie1.dao.DaoSan

class CityRep(private val daoSan: DaoSan) {

    val allCities: LiveData<List<Cities>> = daoSan.getAlphabetizedCities()

    suspend fun insert(city: Cities){
        daoSan.insert(city)
    }
}