package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Cerbung
import com.example.myapplication.databinding.FollowItemsBinding
import com.squareup.picasso.Picasso

class FollowAdapter2(val cerbungs:ArrayList<Cerbung>) :
    RecyclerView.Adapter<FollowAdapter2.FollowViewHolder>(){
    class FollowViewHolder(val binding:FollowItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = FollowItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val url = cerbungs[position].url_gambar
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(url).into(holder.binding.imgPoster)

        with(holder.binding){
            txtJudul.text = cerbungs[position].title
            txtDeskripsi.text = cerbungs[position].desc_short
            txtPembuat.text = cerbungs[position].author
        }

    }
}