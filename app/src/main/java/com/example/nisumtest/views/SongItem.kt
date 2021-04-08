package com.example.nisumtest.views

import android.view.View
import com.example.nisumtest.R
import com.example.nisumtest.databinding.SongItemViewBinding
import com.example.nisumtest.models.ITunesSong
import com.example.nisumtest.utils.loadImageWithGlide
import com.xwray.groupie.viewbinding.BindableItem

class SongItem(private val songItem: ITunesSong) : BindableItem<SongItemViewBinding>() {

    override fun getLayout(): Int = R.layout.song_item_view

    override fun bind(viewBinding: SongItemViewBinding, position: Int) {
        viewBinding.apply {
            textViewSongName.text = songItem.trackName
            textViewSongArtist.text = songItem.artistName
            imageViewSongAlbum.loadImageWithGlide(songItem.albumImage)
        }
    }

    override fun initializeViewBinding(view: View): SongItemViewBinding {
        return SongItemViewBinding.bind(view)
    }

    fun getSong(): ITunesSong = songItem
}