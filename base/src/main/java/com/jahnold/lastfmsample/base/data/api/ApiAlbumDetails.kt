package com.jahnold.lastfmsample.base.data.api

data class ApiAlbumDetails(
    val uuid: String?,
    val name: String?,
    val artist: String?,
    val images: List<ApiImage>?,
    val tracks: ApiTracks?,
    val tags: ApiTags?,
    val wiki: ApiWiki?
)