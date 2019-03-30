package com.jahnold.lastfmsample.list.domain.data

import com.jahnold.lastfmsample.base.data.ImageSize

data class AlbumSearch(
    val uuid: String,
    val name: String,
    val artist: String,
    val images: Map<ImageSize, String>
)