package com.jahnold.lastfmsample.base.data.api

data class ApiSearchAlbum(
    val name: String?,
    val artist: String?,
    val images: List<ApiImage>,
    val uuid: String?
)