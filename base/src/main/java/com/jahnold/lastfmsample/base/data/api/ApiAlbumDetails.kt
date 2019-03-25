package com.jahnold.lastfmsample.base.data.api

import com.google.gson.annotations.SerializedName

data class ApiAlbumDetails(

    @SerializedName("mbid")
    val uuid: String?,
    val name: String?,
    val artist: String?,
    val images: List<ApiImage>?,
    val tracks: ApiTracks?,
    val tags: ApiTags?,
    val wiki: ApiWiki?
)