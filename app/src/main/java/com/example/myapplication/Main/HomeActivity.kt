
package com.example.myapplication.Main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Adapter.CerbungAdapter
import com.example.myapplication.Adapter.GenreAdapter
import com.example.myapplication.data.Cerbung
import com.example.myapplication.data.Genre
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class HomeActivity : Fragment() {

    private lateinit var binding: ActivityMainBinding
    private  var cerbungs:ArrayList<Cerbung> = arrayListOf()
    private  var genres:ArrayList<Genre> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/Story/get_story.php"
        val url2 = "http://10.0.2.2/Story/get_genre.php"
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it.toString())
                var obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("msg")
                    val sType = object : TypeToken<List<Cerbung>>(){}.type
                    cerbungs = Gson().fromJson(data.toString(), sType) as ArrayList<Cerbung>
                    Log.d("apiresult", cerbungs.toString())
                    for (i in 0 until cerbungs.size){
                        val urlGetQtyPar = "http://10.0.2.2/Story/get_qtyparagraph.php?id=" + cerbungs[i].id
                        var stringRequestQtyPar = StringRequest(
                            Request.Method.GET,urlGetQtyPar,
                            {
                                Log.d("apiresult",it)
                                val obj = JSONObject(it)
                                if(obj.getString("result") == "OK"){
                                    val data = obj.getJSONObject("data")
                                    cerbungs[i].qtyPar = data.getInt("qtyPar")
                                    Log.d("apiresult",cerbungs[i].toString())
                                    updateList()
                                }else{
                                    Log.d("apiresult",obj.getString("message").toString())
                                }
                            },
                            {
                                Log.e("apiresult",it.message.toString())
                            })
                        val urlgetLike = "http://10.0.2.2/Story/get_likes.php?id=" + cerbungs[i].id
                        var stringRequestLike = StringRequest(
                            Request.Method.GET,
                            urlgetLike,
                            {
                                Log.d("apiresult",it)
                                val obj = JSONObject(it)
                                if(obj.getString("result") == "OK") {
                                    val data = obj.getJSONObject("data")
                                    cerbungs[i].qtyLikes = data.getInt("qtyLike")
                                    Log.d("apiresult", cerbungs.toString())
                                    updateList()
                                }else{
                                    Log.d("apiresult",obj.getString("message").toString())
                                }
                            },
                            {
                                Log.e("apiresult",it.message.toString())
                            }
                        )
                        q.add(stringRequestQtyPar)
                        q.add(stringRequestLike)
                    }


                }else{
                    Log.d("apiresult",obj.getString("message").toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        )
        val stringRequest2 = StringRequest(
            Request.Method.POST,
            url2,
            {
                Log.d("apiresult", it.toString())
                var obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Genre>>(){}.type
                    genres = Gson().fromJson(data.toString(), sType) as ArrayList<Genre>
                    Log.d("apiresult", genres.toString())

                    updateListGenre()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        )
        q.add(stringRequest)
        q.add(stringRequest2)

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateList() {
        val lm = LinearLayoutManager(activity)
        with(binding.recyclerView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CerbungAdapter(cerbungs)

        }
    }
    fun updateListGenre() {
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        with(binding.recyclerView4) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = GenreAdapter(genres)

        }
    }

}