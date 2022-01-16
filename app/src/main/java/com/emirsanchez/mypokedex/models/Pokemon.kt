package com.emirsanchez.mypokedex.models

data class Pokemon(
    val count: Int,
    val next: String,
    val previous: String,
    var results: ArrayList<Results>
)
