package com.jahnold.lastfmsample.base.data.domain

data class AlbumSearch(
    val uuid: String,
    val name: String,
    val artist: String,
    val images: Map<ImageSize, String>
)