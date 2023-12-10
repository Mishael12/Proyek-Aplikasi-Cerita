package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Genre
import com.example.myapplication.databinding.CerbungItemBinding
import com.example.myapplication.databinding.GenreItemBinding


class GenreAdapter(val genres:ArrayList<Genre>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    class GenreViewHolder(val binding: GenreItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        with(holder.binding){
            chipGenre.text = genres[position].nama
        }
    }

}