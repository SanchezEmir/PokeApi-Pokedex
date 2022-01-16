package com.emirsanchez.mypokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirsanchez.mypokedex.models.DetailsForPokemon
import com.emirsanchez.mypokedex.models.Pokemon
import com.emirsanchez.mypokedex.network.RetrofitDetails
import com.emirsanchez.mypokedex.network.RetrofitForDetails
import com.emirsanchez.mypokedex.network.RetrofitGetPokemons
import com.emirsanchez.mypokedex.network.RetrofitPokemons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainActivityViewModel: ViewModel() {

    var recyclerListLiveData: MutableLiveData<Pokemon> = MutableLiveData()
    var singlePokemonDetails: MutableLiveData<DetailsForPokemon> = MutableLiveData()

    // esta es la función que selecciona la lista de elementos que tenemos en nuestra
    // clase de datos que heredan de MutableLiveData
    fun getRecyclerListObserver(): MutableLiveData<Pokemon> {
        return recyclerListLiveData
    }

    // la función que obtiene los datos de api para listar los pokemones
    fun apiCallPokemons(getID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val retrofitPokemons = RetrofitPokemons.getRetrofitInstance().create(RetrofitGetPokemons::class.java)
                val response = retrofitPokemons.getPokemonForList(0, getID)
                getRecyclerListObserver().postValue(response)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
    }

    // esta es la función que obtiene los detalles de cada pokemon
    fun apiCallPokemonDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val retrofitDetails = RetrofitDetails.getRetrofitDetails().create(RetrofitForDetails::class.java)
            val response = retrofitDetails.getPokemonForDetails(id)
            singlePokemonDetails.postValue(response)
        }
    }

}