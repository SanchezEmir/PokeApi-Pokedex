package com.emirsanchez.mypokedex.models

data class DetailsForPokemon(
    val abilities: List<Abilities>,
    val base_experience: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val types: List<Types>,
    val weight: Int
)
