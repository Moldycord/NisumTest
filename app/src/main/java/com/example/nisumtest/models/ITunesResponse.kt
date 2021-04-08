package com.example.nisumtest.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ITunesResponse(
    @SerializedName("results") val resultList: List<ITunesSong> = emptyList(),
    @SerializedName("resultCount") val count: Int
)

data class ITunesSong(
    @SerializedName("artistId") val artistId: Int,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("artworkUrl60") val albumImage: String,
    @SerializedName("artworkUrl100") val albumImage100: String,
    @SerializedName("collectionName") val albumName: String,
    @SerializedName("collectionId") val collectionId: Int,
    @SerializedName("wrapperType") val wrapperType: String,
    @SerializedName("previewUrl") val previewUrl: String
) : Serializable