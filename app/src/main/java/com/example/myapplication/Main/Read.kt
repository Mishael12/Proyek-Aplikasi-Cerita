package com.example.myapplication.Main

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Adapter.CerbungAdapter
import com.example.myapplication.Adapter.ReadAdapter
import com.example.myapplication.Login.Login
import com.example.myapplication.data.Cerbung
import com.example.myapplication.data.Paragraph
import com.example.myapplication.data.User
import com.example.myapplication.databinding.ReadBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import org.json.JSONObject

class Read : AppCompatActivity()  {
    private lateinit var binding : ReadBinding
    lateinit var paragraphs: ArrayList<Paragraph>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idcerbung = intent.getIntExtra(CerbungAdapter.IDCERBUNG, 0)
        var cerbung = Cerbung(0, "", "", "", "", "", "", 0, 0, 0)

        val q = Volley.newRequestQueue(this)
        val urlRead = "http://10.0.2.2/Story/get_detailCerbung.php?id=" + idcerbung
        val urlParag = "http://10.0.2.2/Story/get_paragraph.php?id=" + idcerbung
        val activeUser = Login.user

        val stringRequestReadCerbung = StringRequest(
            Request.Method.GET,
            urlRead, {
                Log.d("apiresult-cerbung", it)
                var obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONObject("data")
                    var sType = object : TypeToken<Cerbung>() {}.type
                    cerbung = Gson().fromJson(data.toString(), sType)
                    Log.d("apiresult-cerbung", cerbung.toString())
                    val urlGetQtyPar = "http://10.0.2.2/Story/get_qtyparagraph.php?id=" + cerbung.id
                    var stringRequestQtyPar = StringRequest(
                        Request.Method.GET, urlGetQtyPar,
                        {
                            Log.d("apiresult-cerbung", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "OK") {
                                val data = obj.getJSONObject("data")
                                cerbung.qtyPar = data.getInt("qtyPar")
                                Log.d("apiresult-cerbung", cerbung.toString())
                            } else {
                                Log.d("apiresult-cerbung", obj.getString("message").toString())
                            }
                        },
                        {
                            Log.e("apiresult", it.message.toString())
                        })
                    val urlGetLike = "http://10.0.2.2/Story/get_likes.php?id=" + cerbung.id
                    var stringRequestQtyLike = StringRequest(
                        Request.Method.GET, urlGetLike,
                        {
                            Log.d("apiresult", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "OK") {
                                val data = obj.getJSONObject("data")
                                cerbung.qtyLikes = data.getInt("qtyLike")
                                Log.d("apiresult", cerbung.toString())
                            } else {
                                Log.d("apiresult", obj.getString("message").toString())
                            }
                        }, {
                            Log.e("apiresult", it.message.toString())
                        })
                    val urlIsCerbungFollowed =
                        "http://10.0.2.2/Story/get_iscerbungfollowed.php?id_user=" + activeUser.id + "&id_cerbung=" + idcerbung
                    val stringRequestIsFollowed = StringRequest(
                        Request.Method.GET, urlIsCerbungFollowed,
                        {
                            Log.d("apiresult", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "OK") {
                                val data = obj.getJSONObject("data")
                                val isFollowed = data.getInt("isFollowed")
                                Log.d("apiresult", isFollowed.toString())
                                updateUI(cerbung, isFollowed)
                            } else {
                                Log.d("apiresult", obj.getString("message"))
                            }
                        },
                        {
                            Log.e("apiresult", it.message.toString())
                        })
                    q.add(stringRequestQtyPar)
                    q.add(stringRequestQtyLike)
                    q.add(stringRequestIsFollowed)
                } else {
                    Log.d("apiresult", obj.getString("message").toString())
                }
            }, {
                Log.e("apiresult", it.message.toString())
            }
        )
        q.add(stringRequestReadCerbung)
        val stringRequestParagraph = StringRequest(
            Request.Method.GET, urlParag,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    var sType = object : TypeToken<List<Paragraph>>() {}.type
                    paragraphs = Gson().fromJson(data.toString(), sType)
                    Log.d("apiresult", paragraphs.toString())
                    updateListParagraph(activeUser)
                } else {
                    Log.d("apiresult", obj.getString("message").toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequestParagraph)

        with(binding) {
            follow.setOnClickListener {
                val urlIsCerbungFollowed =
                    "http://10.0.2.2/Story/get_iscerbungfollowed.php?id_user=" + activeUser.id + "&id_cerbung=" + idcerbung
                val stringRequestIsFollowed = StringRequest(
                    Request.Method.GET, urlIsCerbungFollowed,
                    {
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            val data = obj.getJSONObject("data")
                            val isFollowed = data.getInt("isFollowed")
                            Log.d("apiresult", data.toString())
                            if (isFollowed == 1) {
                                binding.follow.text = "- Unfollow"
                                val urlUnfollowCerbung =
                                    "http://10.0.2.2/Story/delete_unfollowcerbung.php"
                                val stringRequestFollowCerbung = object : StringRequest(
                                    Method.POST, urlUnfollowCerbung,
                                    {
                                        Log.d("apiresult", it)
                                        var obj = JSONObject(it)
                                        if (obj.getString("result") == "OK") {
                                            Log.d("apiresult", obj.getString("message"))
                                            binding.follow.text = "+ Follow"
                                        } else if (obj.getString("result") == "ERROR") {
                                            Toast.makeText(
                                                this@Read,
                                                "Unable to follow!" + obj.getString("message")
                                                    .toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    {
                                        Log.e("apiresult", it.printStackTrace().toString())
                                    }
                                ) {
                                    override fun getParams(): MutableMap<String, String>? {
                                        val params = HashMap<String, String>()
                                        params["id_user"] = activeUser.id.toString()
                                        params["id_cerbung"] = idcerbung.toString()
                                        return params
                                    }
                                }
                                q.add(stringRequestFollowCerbung)
                            } else {
                                val urlFollowCerbung = "http://10.0.2.2/Story/add_followcerbung.php"
                                val stringRequestFollowCerbung = object : StringRequest(
                                    Method.POST, urlFollowCerbung,
                                    {
                                        Log.d("apiresult", it)
                                        var obj = JSONObject(it)
                                        if (obj.getString("result") == "OK") {
                                            Log.d("apiresult", obj.getString("message"))
                                            binding.follow.text = "- Unfollow"
                                        } else if (obj.getString("result") == "ERROR") {
                                            Toast.makeText(
                                                this@Read,
                                                "Unable to unfollow <error: apierror>" + obj.getString(
                                                    "message"
                                                ).toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    {
                                        Log.e("apiresult", it.printStackTrace().toString())
                                    }
                                ) {
                                    override fun getParams(): MutableMap<String, String>? {
                                        val params = HashMap<String, String>()
                                        params["id_user"] = activeUser.id.toString()
                                        params["id_cerbung"] = idcerbung.toString()
                                        return params
                                    }
                                }
                                q.add(stringRequestFollowCerbung)
                            }
                        } else {
                            Log.d("apiresult", obj.getString("message").toString())
                        }
                    },
                    {
                        Log.e("apiresult", it.message.toString())
                    })
                q.add(stringRequestIsFollowed)
            }
            btnSubmitReadPublic.setOnClickListener {
                if (textInputLayout9.length() <= 225) {
                    val url = "http://10.0.2.2/Story/add_paragraph.php"
                    val stringRequestAddParagraph = object : StringRequest(
                        Method.POST, url,
                        {
                            Log.d("apiresult", it)
                            var obj = JSONObject(it)
                            if (obj.getString("result") == "OK") {
                                Toast.makeText(
                                    this@Read,
                                    obj.getString("message").toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                //Refresh data ketika selesai tambah paragraf
                                q.add(stringRequestReadCerbung)
                                q.add(stringRequestParagraph)
                            } else if (obj.getString("result") == "ERROR") {
                                Toast.makeText(
                                    this@Read,
                                    "Unable to add paragraph! <error: apierror>" + obj.getString("message")
                                        .toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        {
                            Log.e("apiresult", it.printStackTrace().toString())
                            Toast.makeText(
                                this@Read,
                                "Unable to sign up! <error: volleyerror>",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["content"] = binding.textInputLayout9.text.toString()
                            params["id_cerbung"] = idcerbung.toString()
                            params["id_author"] = activeUser.id.toString()
                            return params
                        }
                    }
                    q.add(stringRequestAddParagraph)
                } else {
                    Toast.makeText(this@Read, "Character must be 225 or less!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            btnBack.setOnClickListener{
                val intent = Intent(this@Read, Main::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    fun updateListParagraph(activeUser: User){
        val lm = LinearLayoutManager(this)
        with(binding.recyclerView2){
            layoutManager = lm
            setHasFixedSize(true)
            adapter = ReadAdapter(paragraphs, activeUser)
        }
    }
    fun updateUI(cerbung:Cerbung, isFollowed:Int){
        val url = cerbung.url_gambar
        val builder = Picasso.Builder(this)
        builder.listener { picasso, uri, exception -> exception.printStackTrace()  }
        Picasso.get().load(url).into(binding.imgPosterRead)
        binding.txtJudulRead.text = cerbung.title
        binding.txtQtyParRead.text = cerbung.qtyPar.toString()
        binding.txtLikeRead.text = cerbung.qtyLikes.toString()
        binding.txtPembuatRead.text = "by " + cerbung.author
        binding.txtDateCreated.text = cerbung.tgl_dibuat
        binding.btnGenreRead.text = cerbung.genre

        if(cerbung.isRestricted == 1){
            binding.textInputLayout9.visibility = View.GONE
            binding.btnSubmitReadPublic.text = "+ Request Contribute"
        }else{
            binding.chipAccess.visibility = View.INVISIBLE
        }
        if (isFollowed == 1) {
            binding.follow.text = "Unfollow"
        } else {
            binding.follow.text = "+ Follow"
        }
    }
}