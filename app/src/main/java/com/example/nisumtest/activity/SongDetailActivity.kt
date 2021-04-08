package com.example.nisumtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nisumtest.databinding.ActivitySongDetailBinding
import com.example.nisumtest.models.ITunesSong
import com.example.nisumtest.utils.SELECTED_SONG
import com.example.nisumtest.utils.loadImageWithGlide
import com.example.nisumtest.viewmodels.SongDetailActivityViewModel
import com.example.nisumtest.views.SongItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding
    private lateinit var selectedSong: ITunesSong
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var viewModel: SongDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        selectedSong = intent.getSerializableExtra(SELECTED_SONG) as ITunesSong
        viewModel = ViewModelProvider(this).get(SongDetailActivityViewModel::class.java)
        setContentView(binding.root)
        setupBinding()
        viewModel.getSongsByArtist(selectedSong.collectionId)
    }

    private fun setupBinding() {
        binding.apply {
            imageViewSongAlbum.loadImageWithGlide(selectedSong.albumImage100)
            textViewSongAlbum.text = selectedSong.albumName
            recyclerViewSongs.run {
                layoutManager = LinearLayoutManager(this@SongDetailActivity)
                adapter = groupAdapter
            }
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getSongList().observe(this, Observer { onSongsReceived(it) })
    }

    private fun onSongsReceived(songList: List<SongItem>) {
        groupAdapter.apply {
            clear()
            addAll(songList)
        }
    }
}