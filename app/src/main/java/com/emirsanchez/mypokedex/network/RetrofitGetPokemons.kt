package com.emirsanchez.mypokedex.network

import com.emirsanchez.mypokedex.models.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query

// esta es la interfaz que nos permite establecer el número de imágenes de pokemon
interface RetrofitGetPokemons {

    @GET("pokemon")
    suspend fun getPokemonForList(@Query("offset") query: Int, @Query("limit") lim: Int): Pokemon

}