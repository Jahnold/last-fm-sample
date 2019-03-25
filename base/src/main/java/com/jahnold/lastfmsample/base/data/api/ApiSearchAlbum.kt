package com.jahnold.lastfmsample.base.data.api

import com.google.gson.annotations.SerializedName

data class ApiSearchAlbum(
    val name: String?,
    val artist: String?,

    @SerializedName("image")
    val images: List<ApiImage>?,

    @SerializedName("mbid")
    val uuid: String?
)