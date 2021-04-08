package com.example.nisumtest.network.service

import com.example.nisumtest.models.ITunesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("/search")
    fun searchSongs(
        @Query("term") name: String,
        @Query("limit") limit: Int = 20,
        @Query("mediaType") mediaType: String = "music"
    ): Single<ITunesResponse>

    @GET("/lookup")
    fun getSongsByAlbumId(
        @Query("id") id: Int,
        @Query("entity") entity: String = "song"
    ): Single<ITunesResponse>
}