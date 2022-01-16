package com.emirsanchez.mypokedex.network

import com.emirsanchez.mypokedex.models.DetailsForPokemon
import retrofit2.http.GET
import retrofit2.http.Path

// esta es la interfaz para la API
interface RetrofitForDetails {

    @GET("pokemon/{id}")
    suspend fun getPokemonForDetails(@Path("id") id: String): DetailsForPokemon

}