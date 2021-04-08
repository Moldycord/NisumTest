package com.example.nisumtest.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nisumtest.R
import com.example.nisumtest.databinding.ActivitySongDetailBinding
import com.example.nisumtest.models.ITunesSong
import com.example.nisumtest.utils.SELECTED_SONG
import com.example.nisumtest.utils.loadImageWithGlide
import com.example.nisumtest.viewmodels.SongDetailActivityViewModel
import com.example.nisumtest.views.SongItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.io.IOException

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding
    private lateinit var selectedSong: ITunesSong
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var viewModel: SongDetailActivityViewModel
    private var mMediaPlayer: MediaPlayer = MediaPlayer().apply {
        setOnCompletionListener {
            restartPlayer()
        }
    }
    private var isSourceSet = false

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
            imageViewPlayPause.setOnClickListener { changeIcon() }
        }
        groupAdapter.setOnItemClickListener { item, _ ->
            if (item is SongItem) {
                playSong(item)
            }
        }
        setupObservers()
        loadFirstSong()
    }

    private fun setupObservers() {
        viewModel.getSongList().observe(this, Observer { onSongsReceived(it) })
    }

    private fun playSong(item: SongItem) {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
        }
        isSourceSet = try {
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(item.getSong().previewUrl)
            mMediaPlayer.prepare()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
        showPlayerLayout()
    }

    private fun onSongsReceived(songList: List<SongItem>) {
        groupAdapter.apply {
            clear()
            addAll(songList)
        }
    }

    private fun showPlayerLayout() {
        if (isSourceSet) {
            mMediaPlayer.start()
            binding.imageViewPlayPause.setImageResource(R.drawable.baseline_pause_24)
        }
        updateProgress()
    }

    private fun changeIcon() {
        if (mMediaPlayer.isPlaying) {
            binding.imageViewPlayPause.setImageResource(R.drawable.baseline_play_arrow_24)
            mMediaPlayer.pause()
        } else {
            binding.imageViewPlayPause.setImageResource(R.drawable.baseline_pause_24)
            mMediaPlayer.start()
        }
    }

    private fun loadFirstSong() {
        try {
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(selectedSong.previewUrl)
            mMediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateProgress() {
        binding.seekBarProgressSong.max = mMediaPlayer.duration / 1000
        val mHandler = Handler()
        this.runOnUiThread(object : Runnable {
            override fun run() {
                if (mMediaPlayer.isPlaying) {
                    //  val progress = mMediaPlayer.currentPosition / mMediaPlayer.duration * 100;
                    //binding.seekBarProgressSong.progress = progress

                    val mCurrentPosition = mMediaPlayer.currentPosition / 1000
                    binding.seekBarProgressSong.progress = mCurrentPosition
                }
                mHandler.postDelayed(this, 1000)
            }
        })
    }

    private fun restartPlayer() {
        binding.apply {
            seekBarProgressSong.progress = 0
            imageViewPlayPause.setImageResource(R.drawable.baseline_play_arrow_24)
        }
    }

    override fun onPause() {
        super.onPause()
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
        }
    }
}