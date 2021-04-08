package com.example.nisumtest.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nisumtest.databinding.ActivityMainBinding
import com.example.nisumtest.utils.SELECTED_SONG
import com.example.nisumtest.viewmodels.MainActivityViewModel
import com.example.nisumtest.views.SongItem
import com.google.android.material.internal.TextWatcherAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setContentView(binding.root)
        setupBinding()
        observeLiveData()
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
            recyclerViewSongs.run {
                layoutManager = LinearLayoutManager(this@SearchActivity)
                adapter = groupAdapter
            }
            groupAdapter.setOnItemClickListener { item, _ ->
                if (item is SongItem) {
                    onSongClicked(item)
                }
            }
        }
    }

    private fun observeLiveData() {
        viewModel.getSongList().observe(this, Observer { onSongsReceived(it) })
    }

    private fun onSongsReceived(songList: List<SongItem>) {
        groupAdapter.apply {
            clear()
            addAll(songList)
        }
    }

    private fun onSongClicked(songItem: SongItem) {
        startActivity(Intent(this, SongDetailActivity::class.java).apply {
            putExtra(SELECTED_SONG, songItem.getSong())
        })
    }
}