package com.jahnold.lastfmsample.base.util

import android.app.Application
import android.content.Context
import javax.inject.Inject

class PersistenceHelperImpl @Inject constructor(context: Application) : PersistenceHelper {

    private val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    override fun saveAlbumUuid(uuid: String) {
        prefs.edit().putString(KEY_ALBUM_UUID, uuid).apply()
    }

    override fun getAlbumUuid(): String? {
        return prefs.getString(KEY_ALBUM_UUID, null)
    }

    companion object {

        private const val KEY_ALBUM_UUID = "key_album_uuid"
        private const val PREFS_FILE = "prefs.txt"
    }
}