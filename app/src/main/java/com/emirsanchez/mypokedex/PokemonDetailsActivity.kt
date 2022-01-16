package com.emirsanchez.mypokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emirsanchez.mypokedex.models.DetailsForPokemon
import com.emirsanchez.mypokedex.viewmodel.MainActivityViewModel
import com.squareup.picasso.Picasso

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var connectivityLiveData: ConnectivityLiveData
    lateinit var name: TextView
    lateinit var pokeAbility: TextView
    lateinit var base_experience: TextView
    lateinit var height: TextView
    lateinit var type: TextView
    lateinit var weight: TextView


    private lateinit var card: CardView
    private lateinit var card1: CardView
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        name = findViewById(R.id.name)
        pokeAbility = findViewById(R.id.ability)
        base_experience = findViewById(R.id.base_expe)
        height = findViewById(R.id.height)
        type = findViewById(R.id.types)
        weight = findViewById(R.id.weight)

        card = findViewById(R.id.card2)
        card1 = findViewById(R.id.card1)
        errorMessage = findViewById(R.id.tvError2)
        connectivityLiveData = ConnectivityLiveData(application)

        val newId = intent.getIntExtra("id", 1)

        val id = newId + 1

        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()

        apiCall(id.toString())

    }

    // function to get the details for each pokemon
    private fun bind(it: DetailsForPokemon) {

        name.text = getString(R.string.names, it.name)
        pokeAbility.text = getString(
            R.string.abilities,
            it.abilities[0].ability.name.capitalize()
        )
        base_experience.text = getString(R.string.base_expe, it.base_experience.toString())
        height.text = getString(R.string.height, it.height.toString())
        type.text = getString(
            R.string.types,
            it.types[0].type.name.capitalize()
        )
        weight.text = getString(R.string.weight, it.weight.toString())

        val sprite = findViewById<ImageView>(R.id.imageView)
        val sprite2 = findViewById<ImageView>(R.id.imageView2)
        val firstUrlBack = it.sprites.front_default
        val secondUrlBack = it.sprites.back_default
        Picasso.get()
            .load(firstUrlBack)
            .into(sprite)
        Picasso.get()
            .load(secondUrlBack)
            .into(sprite2)
    }

    // haciendo una llamada a la Api para obtener los detalles de cada pokemon
    private fun apiCall(id: String): MainActivityViewModel {
        val viewModelTwo = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModelTwo.singlePokemonDetails.observe(this, Observer {
            if (it != null) {
                bind(it)
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        })

        connectivityLiveData.observe(this, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                    viewModelTwo.apiCallPokemonDetails(id)
                    errorMessage.visibility = View.GONE
                    card.visibility = View.VISIBLE
                    card1.visibility = View.VISIBLE
                    height.visibility = View.VISIBLE
                    pokeAbility.visibility = View.VISIBLE
                    name.visibility = View.VISIBLE
                }
                false -> {
                    errorMessage.visibility = View.VISIBLE
                    card.visibility = View.GONE
                    height.visibility = View.GONE
                    pokeAbility.visibility = View.GONE
                    card1.visibility = View.GONE
                    name.visibility = View.GONE
                }
            }
        })

        return viewModelTwo
    }

}