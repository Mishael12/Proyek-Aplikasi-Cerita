package com.example.myapplication.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Main.Read
import com.example.myapplication.data.Cerbung
import com.example.myapplication.databinding.CerbungItemBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class CerbungAdapter(val cerbungs:ArrayList<Cerbung>) :
    RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>(){
    companion object{
        val IDCERBUNG = "IDCERBUNG"
    }
    class CerbungViewHolder(val binding:CerbungItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = cerbungs[position].url_gambar
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(url).into(holder.binding.imgPoster)

        with(holder.binding){
            txtJudul.text = cerbungs[position].title
            txtDeskripsi.text = cerbungs[position].desc_short
            txtPembuat.text = cerbungs[position].author
            txtParagraf.text = cerbungs[position].qtyPar.toString()
            txtLike.text = cerbungs[position].qtyLikes.toString()
            btnBaca.setOnClickListener {
                val intent = Intent(it.context, Read::class.java)
                intent.putExtra(IDCERBUNG, cerbungs[position].id)
                it.context.startActivity(intent)
            }
        }

    }
}