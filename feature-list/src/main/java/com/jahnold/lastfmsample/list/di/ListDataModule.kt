package com.jahnold.lastfmsample.list.di

import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.list.data.ListUiModel
import com.jahnold.lastfmsample.list.data.ListUiModelTransformer
import dagger.Module
import dagger.Provides

@Module
object ListDataModule {

    @Provides
    @JvmStatic
    fun providesListDataTransformer(): Transformer<AlbumSearch, ListUiModel> =
        ListUiModelTransformer()
}