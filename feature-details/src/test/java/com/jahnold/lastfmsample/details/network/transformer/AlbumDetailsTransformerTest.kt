@file:Suppress("MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.details.network.transformer

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.data.ImageSize
import com.jahnold.lastfmsample.base.network.data.ApiImage
import com.jahnold.lastfmsample.details.network.data.*
import org.junit.Test

class AlbumDetailsTransformerTest {

    val transformer = AlbumDetailsTransformer()

    @Test
    fun `should transfer simple data from api object`() {

        val result = transformer.transform(ALBUM_DETAILS)

        assertThat(result.uuid).isEqualTo(UUID)
        assertThat(result.name).isEqualTo(NAME)
        assertThat(result.artist).isEqualTo(ARTIST)
    }

    @Test
    fun `should convert images`() {

        val result = transformer.transform(ALBUM_DETAILS)

        assertThat(result.images).hasSize(2)
        assertThat(result.images[ImageSize.SMALL]).isEqualTo(IMAGE_URL1)
        assertThat(result.images[ImageSize.MEDIUM]).isEqualTo(IMAGE_URL2)
    }

    @Test
    fun `should convert tracks`() {

        val result = transformer.transform(ALBUM_DETAILS)

        assertThat(result.tracks).hasSize(1)
        assertThat(result.tracks.first().name).isEqualTo(TRACK_NAME)
        assertThat(result.tracks.first().duration).isEqualTo(TRACK_DURATION)
    }

    @Test
    fun `should convert tags`() {

        val result = transformer.transform(ALBUM_DETAILS)

        assertThat(result.tags).hasSize(1)
        assertThat(result.tags.first()).isEqualTo(TAG_NAME)
    }

    @Test
    fun `should convert wiki`() {

        val result = transformer.transform(ALBUM_DETAILS)

        assertThat(result.published).isEqualTo(PUBLISHED)
        assertThat(result.summary).isEqualTo(SUMMARY)
    }

    companion object {

        const val UUID = "uuid"
        const val NAME = "name"
        const val ARTIST = "artist"

        const val IMAGE_URL1 = "url1"
        const val IMAGE_URL2 = "url2"

        const val TRACK_NAME = "track"
        const val TRACK_DURATION = "1:23"

        const val TAG_NAME = "tag"

        const val PUBLISHED = "published"
        const val SUMMARY = "summary"

        val ALBUM_DETAILS = ApiAlbumDetails(
            album = ApiAlbumDetailsAlbum(
                uuid = UUID,
                name = NAME,
                artist = ARTIST,
                image = listOf(
                    ApiImage(url = IMAGE_URL1, size = "small"),
                    ApiImage(url = IMAGE_URL2, size = "medium")
                ),
                tracks = ApiTracks(
                    track = listOf(
                        ApiTrack(
                            name = TRACK_NAME,
                            duration = TRACK_DURATION
                        )
                    )
                ),
                tags = ApiTags(
                    tag = listOf(
                        ApiTag(TAG_NAME)
                    )
                ),
                wiki = ApiWiki(
                    published = PUBLISHED,
                    summary = SUMMARY
                )
            )
        )
    }
}