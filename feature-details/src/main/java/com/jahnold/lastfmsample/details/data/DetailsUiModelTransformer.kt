package com.jahnold.lastfmsample.details.data

import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.data.domain.ImageSize
import com.jahnold.lastfmsample.base.transformers.Transformer

class DetailsUiModelTransformer: Transformer<AlbumDetails, DetailsUiModel> {

    override fun transform(input: AlbumDetails): DetailsUiModel {

        return DetailsUiModel(
            name = input.name,
            artist = input.artist,
            published = input.published,
            summary = input.summary,
            imageUrl = getImageUrl(input.images)
        )
    }

    private fun getImageUrl(images: Map<ImageSize, String>): String? {

        if (images.containsKey(ImageSize.EXTRA_LARGE)) return images.getValue(ImageSize.EXTRA_LARGE)
        if (images.containsKey(ImageSize.LARGE)) return images.getValue(ImageSize.LARGE)
        if (images.containsKey(ImageSize.MEDIUM)) return images.getValue(ImageSize.MEDIUM)
        if (images.containsKey(ImageSize.SMALL)) return images.getValue(ImageSize.SMALL)

        return null
    }
}