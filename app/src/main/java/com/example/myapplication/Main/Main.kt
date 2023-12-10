package com.example.myapplication.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Adapter.MyAdapter
import com.example.myapplication.Controller
import com.example.myapplication.R
import com.example.myapplication.databinding.MainBinding
import com.example.myapplication.databinding.TestingBinding

class Main : AppCompatActivity() {
    private lateinit var binding : MainBinding
    private var fragments:ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fragments.add(HomeActivity())
        fragments.add(Follow())
        fragments.add(Controller())
        fragments.add(test())
        fragments.add(Profile())
//        fragments.add(test())
        binding.view.adapter = MyAdapter(this, fragments)
        binding.view.registerOnPageChangeCallback(
            object:ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.navi.selectedItemId =
                        binding.navi.menu.getItem(position).itemId
                }
        })
        binding.navi.setOnItemSelectedListener {
            binding.view.currentItem = when(it.itemId){
                R.id.home -> 0
                R.id.follow -> 1
                R.id.Create -> 2
                R.id.User -> 3
                R.id.prof -> 4
                else -> 0
            }
            true
        }
    }
}