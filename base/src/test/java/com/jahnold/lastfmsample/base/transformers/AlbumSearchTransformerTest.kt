@file:Suppress("MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.base.transformers

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.network.data.ApiImage
import com.jahnold.lastfmsample.list.network.data.ApiSearchAlbum
import com.jahnold.lastfmsample.base.data.ImageSize
import org.junit.Test

class AlbumSearchTransformerTest {

    val transformer = com.jahnold.lastfmsample.list.network.transformer.AlbumSearchTransformer()

    @Test
    fun `should transfer simple data from api object`() {

        val result = transformer.transform(SEARCH_ALBUM)

        assertThat(result.uuid).isEqualTo(AlbumDetailsTransformerTest.UUID)
        assertThat(result.name).isEqualTo(AlbumDetailsTransformerTest.NAME)
        assertThat(result.artist).isEqualTo(AlbumDetailsTransformerTest.ARTIST)
    }

    @Test
    fun `should convert images`() {

        val result = transformer.transform(SEARCH_ALBUM)

        assertThat(result.images).hasSize(2)
        assertThat(result.images[ImageSize.SMALL]).isEqualTo(AlbumDetailsTransformerTest.IMAGE_URL1)
        assertThat(result.images[ImageSize.MEDIUM]).isEqualTo(AlbumDetailsTransformerTest.IMAGE_URL2)
    }

    companion object {

        const val UUID = "uuid"
        const val NAME = "name"
        const val ARTIST = "artist"

        const val IMAGE_URL1 = "url1"
        const val IMAGE_URL2 = "url2"

        val SEARCH_ALBUM = com.jahnold.lastfmsample.list.network.data.ApiSearchAlbum(
            uuid = UUID,
            name = NAME,
            artist = ARTIST,
            images = listOf(
                ApiImage(url = IMAGE_URL1, size = "small"),
                ApiImage(url = IMAGE_URL2, size = "medium")
            )
        )
    }
}