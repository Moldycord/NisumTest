package com.example.nisumtest.network.repository

import com.example.nisumtest.models.ITunesResponse
import com.example.nisumtest.network.ServiceBuilder
import com.example.nisumtest.network.service.ITunesService
import io.reactivex.Single

class ITunesRepository {

    private var retrofit: ITunesService = ServiceBuilder.getService()

    fun searchSong(name: String): Single<ITunesResponse> {
        return retrofit.searchSongs(name)
    }

    fun getSongsByAlbumId(albumId: Int): Single<ITunesResponse> {
        return retrofit.getSongsByAlbumId(albumId)
    }
}