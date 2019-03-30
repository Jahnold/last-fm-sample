@file:Suppress("MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.list.view.data

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.list.domain.data.AlbumSearch
import com.jahnold.lastfmsample.base.data.ImageSize
import org.junit.Test

class ListUiModelTransformerTest {

    val transformer = ListUiModelTransformer()

    @Test
    fun `should pass through basic details`() {

        val result = transformer.transform(ALBUM)

        assertThat(result.uuid).isEqualTo(UUID)
        assertThat(result.name).isEqualTo(NAME)
        assertThat(result.artist).isEqualTo(ARTIST)
    }

    @Test
    fun `should select the smallest image`() {

        val result = transformer.transform(ALBUM)

        assertThat(result.imageUrl).isEqualTo(IMAGE_URL1)
    }

    @Test
    fun `should send null imageUrl if there are no images`() {

        val album = ALBUM.copy(images = emptyMap())
        val result = transformer.transform(album)

        assertThat(result.imageUrl).isNull()
    }

    companion object {

        const val UUID = "uuid"
        const val NAME = "name"
        const val ARTIST = "artist"

        const val IMAGE_URL1 = "url1"
        const val IMAGE_URL2 = "url2"

        val images = mapOf(
            ImageSize.MEDIUM to IMAGE_URL1,
            ImageSize.EXTRA_LARGE to IMAGE_URL2
        )

        val ALBUM = AlbumSearch(
            uuid = UUID,
            name = NAME,
            artist = ARTIST,
            images = images
        )
    }
}