package com.example.myapplication.Main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Login.Login
import com.example.myapplication.Login.Register
import com.example.myapplication.R
import com.example.myapplication.databinding.FollowBinding
import com.example.myapplication.databinding.TestingBinding

class Profile : Fragment() {
    private lateinit var binding : TestingBinding
    val user = Login.user
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.EditProfile.setOnClickListener(){
            val intent = Intent (getActivity(), EditProfile::class.java)
            getActivity()?.startActivity(intent)
        }
        binding.logout1.setOnClickListener(){
            val intent = Intent (getActivity(), Login::class.java)
            getActivity()?.startActivity(intent)
        }
        binding.showName.text = user.username
    }
}