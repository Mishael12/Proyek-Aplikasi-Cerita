package com.example.myapplication.Main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.CreateBinding


class create : Fragment() {
    private lateinit var binding: CreateBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            nextbtn.setOnClickListener{
                val direction = createDirections.actionCreateToCreate2(Judultxt.text.toString(), DecsTxt.text.toString(), Genre.text.toString(), UrlSource.text.toString())
                findNavController().navigate(direction)
            }

        }
    }
}