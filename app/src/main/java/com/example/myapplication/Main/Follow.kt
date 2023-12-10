//package com.example.myapplication
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myapplication.databinding.FollowBinding
//
//class Follow : AppCompatActivity() {
//    private lateinit var binding : FollowBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FollowBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val lm: LinearLayoutManager = LinearLayoutManager(this)
//        lm.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView3.layoutManager = lm
//        binding.recyclerView3.setHasFixedSize(true)
//        binding.recyclerView3.adapter = followAdapter()
//    }
//}
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
import com.example.myapplication.Adapter.FollowAdapter2
import com.example.myapplication.databinding.FollowBinding
import com.example.myapplication.Login.Login
import com.example.myapplication.data.Cerbung
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class Follow : Fragment() {
    private lateinit var binding: FollowBinding
    private  var cerbungs:ArrayList<Cerbung> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun updateList(){
        binding.recyclerView3.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView3.setHasFixedSize(true)
        binding.recyclerView3.adapter = FollowAdapter2(cerbungs)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Login.user
        var q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/Story/following.php?id="+user.id
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it.toString())
                var obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() {}.type
                    cerbungs = Gson().fromJson(data.toString(), sType) as ArrayList<Cerbung>
                    Log.d("apiresult", cerbungs.toString())

                    updateList()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        )
        q.add(stringRequest)
    }
}
