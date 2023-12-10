package com.example.myapplication.Login

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Main.Main
import com.example.myapplication.data.User
import com.example.myapplication.databinding.LoginBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class Login : AppCompatActivity()  {
    private lateinit var binding: LoginBinding
    companion object {var user = User(0,"","","")}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/Story/login.php"
        var stringRequest = object : StringRequest(
            Method.POST,
            url,
            {
                Log.d("apiresult", it)
                var obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    var data = obj.getJSONObject("data")
                    var sType = object : TypeToken<User>() {}.type
                    user = Gson().fromJson(data.toString(), sType)
                    Log.d("apiresult", user.toString())
                    startActivity(Intent(this, Main::class.java))
                    finish()
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
                params["username"] = binding.username.text.toString()
                params["password"] = binding.password.text.toString()
                return params
            }
        }
        binding.loginbtn.setOnClickListener(){
            q.add(stringRequest)

        }
        binding.register.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}