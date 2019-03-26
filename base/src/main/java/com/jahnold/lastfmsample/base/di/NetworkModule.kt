package com.jahnold.lastfmsample.base.di

import com.jahnold.lastfmsample.base.BuildConfig
import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchAlbum
import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.network.QueryParamsInterceptor
import com.jahnold.lastfmsample.base.network.RestApi
import com.jahnold.lastfmsample.base.transformers.AlbumDetailsTransformer
import com.jahnold.lastfmsample.base.transformers.AlbumSearchTransformer
import com.jahnold.lastfmsample.base.transformers.Transformer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    internal fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttp(
        queryParamsInterceptor: QueryParamsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(queryParamsInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @JvmStatic
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .validateEagerly(true)
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun providesRestApi(retrofit: Retrofit): RestApi =
            retrofit.create(RestApi::class.java)

    @Provides
    @JvmStatic
    fun providesAlbumDetailsTransformer(): Transformer<ApiAlbumDetails, AlbumDetails> =
        AlbumDetailsTransformer()

    @Provides
    @JvmStatic
    fun providesAlbumSearchTransformer(): Transformer<ApiSearchAlbum, AlbumSearch> =
        AlbumSearchTransformer()
}