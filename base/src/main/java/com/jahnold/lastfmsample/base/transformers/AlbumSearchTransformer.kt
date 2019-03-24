package com.jahnold.lastfmsample.base.transformers

import com.jahnold.lastfmsample.base.data.api.ApiImage
import com.jahnold.lastfmsample.base.data.api.ApiSearchAlbum
import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.data.domain.ImageSize

class AlbumSearchTransformer: Transformer<ApiSearchAlbum, AlbumSearch> {

    override fun transform(input: ApiSearchAlbum): AlbumSearch {

        return AlbumSearch(
            uuid = input.uuid.orEmpty(),
            name = input.name.orEmpty(),
            artist = input.artist.orEmpty(),
            images = getImages(input.images)
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
}