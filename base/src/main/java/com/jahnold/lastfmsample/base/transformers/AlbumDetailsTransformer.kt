package com.jahnold.lastfmsample.base.transformers

import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiImage
import com.jahnold.lastfmsample.base.data.api.ApiTags
import com.jahnold.lastfmsample.base.data.api.ApiTracks
import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.data.domain.AlbumTrack
import com.jahnold.lastfmsample.base.data.domain.ImageSize

class AlbumDetailsTransformer: Transformer<ApiAlbumDetails, AlbumDetails> {

    override fun transform(input: ApiAlbumDetails): AlbumDetails {

        return AlbumDetails(
            uuid = input.uuid.orEmpty(),
            name = input.name.orEmpty(),
            artist = input.artist.orEmpty(),
            images = getImages(input.images),
            tracks = getTracks(input.tracks),
            tags = getTags(input.tags),
            published = input.wiki?.published.orEmpty(),
            summary = input.wiki?.summary.orEmpty()
        )
    }

    private fun getImages(apiImages: List<ApiImage>?): Map<ImageSize, String> {

        return apiImages
            ?.mapNotNull { apiImage ->
                val size =  apiImage.size?.let { ImageSize.fromString(it) }
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