package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.User
import com.example.myapplication.databinding.CerbungItemBinding

class AuthorAdapter(val authors:ArrayList<User>) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>(){
    class AuthorViewHolder(val binding: CerbungItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorAdapter.AuthorViewHolder {
        val binding = CerbungItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return AuthorAdapter.AuthorViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return authors.size
    }
    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        with(holder.binding){
            txtPembuat.text = authors[position].username
        }
    }


}