package com.example.nisumtest.network.service

import com.example.nisumtest.models.ITunesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("/search")
    fun searchSongs(@Query("term") name: String, @Query("limit") limit: Int): Single<ITunesResponse>
}