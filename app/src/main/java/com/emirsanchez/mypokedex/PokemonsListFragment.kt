package com.emirsanchez.mypokedex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emirsanchez.mypokedex.adapter.PokemonViewAdapter
import com.emirsanchez.mypokedex.models.Pokemon
import com.emirsanchez.mypokedex.models.Results
import com.emirsanchez.mypokedex.viewmodel.MainActivityViewModel


class PokemonsListFragment : Fragment() {

    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var recyclerAdapter: PokemonViewAdapter
    private lateinit var searchButton: Button
    private lateinit var searchId: EditText
    private lateinit var msgError: TextView
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemons_list, container, false)

        connectivityLiveData = ConnectivityLiveData(requireActivity().application)
        searchButton = view.findViewById(R.id.button2)
        searchId = view.findViewById(R.id.searchID)
        recycler = view.findViewById(R.id.recyclerView)
        msgError = view.findViewById(R.id.tvError)
        var getID = 200
        myViewModel(getID)

        initViewModel(view)
        searchButton.setOnClickListener {
            getID = searchId.text.toString().toInt()

            if (getID == 0) {
                Toast.makeText(activity, "press valid number", Toast.LENGTH_SHORT).show()
            } else {
                myViewModel(getID)
            }
        }

        return view
    }

    // esta es la función que conecta nuestro adaptador recyclerview con un escuchador cuando
    // se hace clic en una imagen
    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerAdapter = PokemonViewAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.setOnItemClickListener(object : PokemonViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, value: View?) {
                val id = position
                val intent = Intent(activity, PokemonDetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })
    }

    // aquí es donde observamos la llamada a la API
    private fun myViewModel(getID: Int) {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(viewLifecycleOwner, Observer<Pokemon> {
            if (it != null) {
                recyclerAdapter.setUpdatedData(it.results as ArrayList<Results>)

            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.apiCallPokemons(getID)
        connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
            //2
            when (isAvailable) {
                true -> {
                    //3
                    recycler.visibility = View.VISIBLE
                    msgError.visibility = View.GONE
                }
                false -> {
                    recycler.visibility = View.GONE
                    msgError.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonsListFragment()
    }

}