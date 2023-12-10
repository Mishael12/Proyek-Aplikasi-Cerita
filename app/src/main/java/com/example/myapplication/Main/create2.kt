package com.example.myapplication.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.Login.Register
import com.example.myapplication.R
import com.example.myapplication.databinding.Create2pageBinding
import com.example.myapplication.databinding.CreateBinding

class create2 : Fragment() {
    private lateinit var binding: Create2pageBinding
    private val args : create2Args by navArgs()

    private var Judul = ""
    private var Decs = ""
    private var Genre = ""
    private var Url = ""
    private var IsRestricted = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Create2pageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Judul = args.judul
        Decs = args.decs
        Genre = args.genre
        Url = args.url
        binding.apply {
            val selected = binding.radioButton.checkedRadioButtonId
            Log.d("Test", binding.Restric.id.toString())
            Log.d("Test2", selected.toString())
            nextbtn.setOnClickListener {
                if(binding.Restric.id == selected){
                    IsRestricted = 1
                }
                else{
                    IsRestricted = 0
                }
                val direction = create2Directions.actionCreate2ToCreate33(Judul, Decs, Cerita.text.toString(), Genre, IsRestricted, Url)
                findNavController().navigate(direction)
            }
            prevbtn.setOnClickListener{
                val direction = create2Directions.actionCreate2ToCreate4(Judul, Decs)
                findNavController().navigate(direction)
            }
        }
    }
}