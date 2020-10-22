package com.example.poopie1.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.poopie1.BD.CityBD
import com.example.poopie1.BD.CityRep
import com.example.poopie1.Cities.Cities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//City view model
class vmodel(application: Application) : AndroidViewModel(application) {

    private val repository: CityRep
    val allCities: LiveData<List<Cities>>

    init {
        val daoSan = CityBD.getDatabase(application, viewModelScope).daoSan()
        repository = CityRep(daoSan)
        allCities = repository.allCities
    }

    fun insert(cities: Cities) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cities)
    }

}