package com.example.myapplication.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Login.Login
import com.example.myapplication.data.Cerbung
import com.example.myapplication.data.User
import com.example.myapplication.databinding.EditprofileBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class EditProfile : AppCompatActivity() {
    private lateinit var binding : EditprofileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Login.user

        var q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/Story/update_user.php"
        var stringRequest = object : StringRequest(
            Method.POST,
            url,
            {
                Log.d("apiresult", it)
                var obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    var data = obj.getJSONObject("message")
                    Log.d("apiresult", data.toString())
                } else if (obj.getString("result") == "ERROR") {
                    Toast.makeText(this, obj.getString("message").toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = user.id.toString()
                params["username"] = binding.Username.text.toString()
                params["password"] = binding.password.text.toString()
                return params
            }
        }

        binding.EditProfile.setOnClickListener(){
            q.add(stringRequest)
        }

        binding.Username.hint = user.username
        binding.password.hint = user.password

        binding.backbutton.setOnClickListener(){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
    }
}