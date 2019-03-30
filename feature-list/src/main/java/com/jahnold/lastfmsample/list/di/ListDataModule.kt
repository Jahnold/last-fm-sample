package com.jahnold.lastfmsample.list.di

import com.jahnold.lastfmsample.list.network.transformer.AlbumSearchTransformer
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.list.domain.data.AlbumSearch
import com.jahnold.lastfmsample.list.network.ListApi
import com.jahnold.lastfmsample.list.network.data.ApiSearchAlbum
import com.jahnold.lastfmsample.list.view.data.ListUiModel
import com.jahnold.lastfmsample.list.view.data.ListUiModelTransformer
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ListDataModule {

    @Provides
    @JvmStatic
    fun providesListDataTransformer(): Transformer<AlbumSearch, ListUiModel> =
        ListUiModelTransformer()

    @Provides
    @JvmStatic
    fun providesAlbumSearchTransformer(): Transformer<ApiSearchAlbum, AlbumSearch> =
        AlbumSearchTransformer()

    @Provides
    @JvmStatic
    @Singleton
    fun providesDetailsApi(retrofit: Retrofit): ListApi =
        retrofit.create(ListApi::class.java)
}