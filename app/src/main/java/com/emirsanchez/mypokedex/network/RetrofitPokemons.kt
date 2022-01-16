package com.emirsanchez.mypokedex.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPokemons {

    //constantes y metodos estaticos en kotlin
    // es parte de una clase pero no necesitas un objeto para usarlo
    // dentro de esta van las constantes y metdos staticos raaaaaaaaaa

    companion object {
        val BaseURL = "https://pokeapi.co/api/v2/"

        //esta es la función que accede a pokemon baseuRL para hacer una llamada api
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}