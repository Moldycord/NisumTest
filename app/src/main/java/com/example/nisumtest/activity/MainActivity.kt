package com.example.nisumtest.activity

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nisumtest.databinding.ActivityMainBinding
import com.example.nisumtest.viewmodel.MainActivityViewModel
import com.google.android.material.internal.TextWatcherAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setContentView(binding.root)
        setupBinding()
    }

    private fun setupBinding() {
        binding.apply {
            editTextSearch.addTextChangedListener(object : TextWatcherAdapter() {
                override fun afterTextChanged(editable: Editable) {
                    if (editable.length >= 5) {
                        viewModel.searchSong(editable.toString())
                    }
                }
            })
        }
    }
}