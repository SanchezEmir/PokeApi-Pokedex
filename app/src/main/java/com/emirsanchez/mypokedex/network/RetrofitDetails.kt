package com.emirsanchez.mypokedex.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDetails {

    companion object {
        val detailsURL = "https://pokeapi.co/api/v2/"

        fun getRetrofitDetails(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(detailsURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}