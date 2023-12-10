package com.example.myapplication.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.data.Paragraph
import com.example.myapplication.data.User
import com.example.myapplication.databinding.ParagrafItemBinding
import org.json.JSONObject

class ReadAdapter(val paragraphs:ArrayList<Paragraph>, val activeUser: User):RecyclerView.Adapter<ReadAdapter.ReadViewHolder>() {
    class ReadViewHolder(val binding: ParagrafItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadViewHolder {
        val binding = ParagrafItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReadViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return paragraphs.size
    }
    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
        with(holder.binding){
            txtParItem.text = paragraphs[position].content
            txtPembuatParItem.text = paragraphs[position].author
            for(i in 0 until paragraphs.size){
                val q = Volley.newRequestQueue(holder.itemView.context)
                val urlGetIsParagraphLiked = "http://10.0.2.2/Story/get_isparagraphliked.php?id_user=" + activeUser.id + "&id_paragraphs=" + paragraphs[position].id
                var stringRequestIsParagraphLiked = StringRequest(
                    Request.Method.GET,urlGetIsParagraphLiked,
                    {
                        Log.d("apiresult-isParagraphLiked",it)
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            val data = obj.getJSONObject("data")
                            val isLiked = data.getInt("isLiked")
                            Log.d("apiresult-isParagraphLiked", data.toString())
                            btnLike.isChecked = isLiked == 1
                        }else{
                            Log.d("apiresult-isParagraphLiked",obj.getString("message").toString())
                        }
                    },
                    {
                        Log.e("apiresult-isParagraphLiked",it.message.toString())
                    })
                q.add(stringRequestIsParagraphLiked)
            }
            btnLike.setOnClickListener{
                if(btnLike.isChecked == true){
                    TODO("API Unlike")
                }else{
                    TODO("API Like")
                }
            }
        }
    }
}