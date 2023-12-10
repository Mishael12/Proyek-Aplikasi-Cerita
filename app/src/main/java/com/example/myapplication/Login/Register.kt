package com.example.myapplication.Login

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Main.Profile
import com.example.myapplication.databinding.RegisterBinding
import org.json.JSONObject

class Register : AppCompatActivity()  {
    private lateinit var binding: RegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/Story/register.php"
        val stringRequest = object : StringRequest(
            Method.POST, url,
            {
                Log.d("apiresult", it)
                var obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    Toast.makeText(this, "Sign Up Success!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else if (obj.getString("result") == "ERROR") {
                    Toast.makeText(
                        this,
                        "Unable to sign up! <error: apierror>" + obj.getString("message")
                            .toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            {
                Log.e("apiresult", it.printStackTrace().toString())
                Toast.makeText(this, "Unable to sign up! <error: volleyerror>", Toast.LENGTH_SHORT)
                    .show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = binding.username.text.toString()
                params["password"] = binding.password.text.toString()
                return params
            }
        }
        binding.loginbtn.setOnClickListener() {
            if (binding.confirm.text.toString() == binding.password.text.toString()) {
                q.add(stringRequest)
            } else {
                Toast.makeText(
                    this,
                    "Password tidak sama",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.account.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}