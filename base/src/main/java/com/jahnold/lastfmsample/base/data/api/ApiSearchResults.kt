package com.jahnold.lastfmsample.base.data.api

import com.google.gson.annotations.SerializedName

data class ApiSearchResults(
    val results: ApiResults?
)

data class ApiResults(

    @SerializedName("albummatches")
    val albumMatches: ApiAlbums?
)

data class ApiAlbums(
    val album: List<ApiSearchAlbum>?
)