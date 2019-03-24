package com.jahnold.lastfmsample.base.util

interface PersistenceHelper {

    fun saveAlbumUuid(uuid: String)
    fun getAlbumUuid(): String?
}