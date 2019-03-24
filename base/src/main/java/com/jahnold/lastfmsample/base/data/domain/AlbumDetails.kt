package com.jahnold.lastfmsample.base.data.domain

data class AlbumDetails(
    val uuid: String,
    val name: String,
    val artist: String,
    val images: Map<ImageSize, String>,
    val tracks: List<AlbumTrack>,
    val tags: List<String>,
    val published: String,
    val summary: String
)