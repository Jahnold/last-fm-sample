package com.jahnold.lastfmsample.details.di

import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.details.data.DetailsUiModel
import com.jahnold.lastfmsample.details.data.DetailsUiModelTransformer
import dagger.Module
import dagger.Provides

@Module
object DetailsDataModule {

    @Provides
    @JvmStatic
    fun providesDetailsDataTransformer(): Transformer<AlbumDetails, DetailsUiModel> =
        DetailsUiModelTransformer()
}