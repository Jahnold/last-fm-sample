package com.jahnold.lastfmsample.details.network.transformer

import com.jahnold.lastfmsample.base.data.ImageSize
import com.jahnold.lastfmsample.base.network.data.ApiImage
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.details.domain.data.AlbumDetails
import com.jahnold.lastfmsample.details.domain.data.AlbumTrack
import com.jahnold.lastfmsample.details.network.data.ApiAlbumDetails
import com.jahnold.lastfmsample.details.network.data.ApiTags
import com.jahnold.lastfmsample.details.network.data.ApiTracks

class AlbumDetailsTransformer: Transformer<ApiAlbumDetails, AlbumDetails> {

    override fun transform(input: ApiAlbumDetails): AlbumDetails {

        val album = input.album

        return AlbumDetails(
            uuid = album?.uuid.orEmpty(),
            name = album?.name.orEmpty(),
            artist = album?.artist.orEmpty(),
            images = getImages(album?.image),
            tracks = getTracks(album?.tracks),
            tags = getTags(album?.tags),
            published = album?.wiki?.published.orEmpty(),
            summary = album?.wiki?.summary.orEmpty()
        )
    }

    private fun getImages(apiImages: List<ApiImage>?): Map<ImageSize, String> {

        return apiImages
            ?.mapNotNull { apiImage ->
                val size = ImageSize.fromString(apiImage.size)
                val url = apiImage.url

                return@mapNotNull when (size != null && url != null) {
                    true -> Pair(size, url)
                    else -> null
                }
            }
            ?.toMap()
            .orEmpty()
    }

    private fun getTracks(apiTracks: ApiTracks?): List<AlbumTrack> {

        return apiTracks
            ?.track
            ?.map { apiTrack ->
                AlbumTrack(
                    name = apiTrack.name.orEmpty(),
                    duration = apiTrack.duration.orEmpty()
                )
            }
            .orEmpty()
    }

    private fun getTags(tags: ApiTags?): List<String> {

        return tags
            ?.tag
            ?.map { apiTag -> apiTag.name.orEmpty() }
            .orEmpty()
    }
}