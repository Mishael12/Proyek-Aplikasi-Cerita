package com.example.myapplication.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Login.Login
import com.example.myapplication.R
import com.example.myapplication.databinding.Create2pageBinding
import com.example.myapplication.databinding.OverviewBinding
import com.example.myapplication.databinding.PindahcreateBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class create3 : Fragment() {
    private lateinit var binding: OverviewBinding

    private val args : create3Args by navArgs()
    private var Judul = ""
    private var Decs = ""
    private var Cerita = ""
    private var Genre = ""
    private var Isrestricted = 1
    private var Url = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OverviewBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Judul = args.judul
        Decs = args.decs
        Genre = args.genre
        Cerita = args.cerita
        Isrestricted = args.isrestricted
        val activeUser = Login.user

        Url = args.url
        binding.apply {
            viewJudul.text = Judul
            btnGenreRead.text = Genre
            DecsView.text = Decs
            TxtViewCerita.text = Cerita
            Author.text = activeUser.username

            val builder = Picasso.Builder(requireContext())
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            Picasso.get().load(Url).into(binding.Gambar)
            if(Isrestricted == 0){
                btnAccess.visibility = View.INVISIBLE
            }else{
                btnAccess.visibility = View.VISIBLE
            }
            SubmitBtn.setOnClickListener{
                val q = Volley.newRequestQueue(it.context)
                val url = "http://10.0.2.2/Story/add_cerbung.php"
                val stringRequest = object : StringRequest(
                    Method.POST, url,
                    {
                        Log.d("apiresult", it)
                        var obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            Log.d("apiresult add cerbung", obj.getString("message").toString())
                        } else if (obj.getString("result") == "ERROR") {
                            Log.d("apiresult add cerbung", obj.getString("message").toString())
                        }
                    },
                    {
                        Log.e("apiresult", it.printStackTrace().toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["id_user"] = Login.user.id.toString()
                        params["title"] = Judul
                        params["desc_short"] = Decs
                        params["url_gambar"] = Url
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }
    }
}