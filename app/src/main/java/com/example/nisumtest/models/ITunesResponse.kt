package com.example.nisumtest.models

import com.google.gson.annotations.SerializedName

data class ITunesResponse(
    @SerializedName("results") val resultList: List<ITunesSong> = emptyList(),
    @SerializedName("resultCount") val count: Int
)

data class ITunesSong(
    @SerializedName("artistId") val artistId: Int
)