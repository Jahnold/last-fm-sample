package com.jahnold.lastfmsample.base.data.api

import com.google.gson.annotations.SerializedName

data class ApiAlbumDetails(
    val album: ApiAlbumDetailsAlbum?
)

data class ApiAlbumDetailsAlbum(
    @SerializedName("mbid")
    val uuid: String?,
    val name: String?,
    val artist: String?,
    val image: List<ApiImage>?,
    val tracks: ApiTracks?,
    val tags: ApiTags?,
    val wiki: ApiWiki?
)