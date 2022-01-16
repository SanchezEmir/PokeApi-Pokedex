package com.emirsanchez.mypokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emirsanchez.mypokedex.Functions
import com.emirsanchez.mypokedex.R
import com.emirsanchez.mypokedex.models.Results
import com.squareup.picasso.Picasso

class PokemonViewAdapter(): RecyclerView.Adapter<PokemonViewAdapter.MyViewHolder>() {

    private var result = ArrayList<Results>()
    private lateinit var nListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position:Int,value: View?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        nListener = listener
    }

    fun setUpdatedData(result: ArrayList<Results>){
        this.result = result
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View, listener: OnItemClickListener ): RecyclerView.ViewHolder(view){
        val imageThumb = view.findViewById<ImageView>(R.id.imageThumb)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition,itemView)
            }
        }

        fun bind(data: Results){
            tvTitle.text = data.name
            val myUrl = data.url

            val getImageNumber = Functions.getUrlNumber(myUrl)
            Picasso.get()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getImageNumber}.png")
                .into(imageThumb)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false )
        return MyViewHolder(view, nListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(result[position])
    }

    override fun getItemCount(): Int {
        return result.size
    }


}