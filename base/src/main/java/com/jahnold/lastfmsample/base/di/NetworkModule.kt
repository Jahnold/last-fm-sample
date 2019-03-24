package com.jahnold.lastfmsample.base.di

import com.jahnold.lastfmsample.base.BuildConfig
import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchAlbum
import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.network.RestApi
import com.jahnold.lastfmsample.base.transformers.AlbumDetailsTransformer
import com.jahnold.lastfmsample.base.transformers.AlbumSearchTransformer
import com.jahnold.lastfmsample.base.transformers.Transformer
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Named("API_KEY")
    @JvmStatic
    internal fun providesApiKey() =
        Interceptor { chain ->
            val newRequest = chain.request().let { request ->
                val newUrl = request.url().newBuilder()
                    .addQueryParameter("api_key", BuildConfig.LAST_FM_APIKEY)
                    .build()
                request.newBuilder()
                    .url(newUrl)
                    .build()
            }
            chain.proceed(newRequest)
        }

    @Provides
    @Named("JSON")
    @JvmStatic
    internal fun providesJson() =
        Interceptor { chain ->
            val newRequest = chain.request().let { request ->
                val newUrl = request.url().newBuilder()
                    .addQueryParameter("format", "json")
                    .build()
                request.newBuilder()
                    .url(newUrl)
                    .build()
            }
            chain.proceed(newRequest)
        }

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttp(
        @Named("API_KEY") apiKeyInterceptor: Interceptor,
        @Named("JSON") jsonInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(jsonInterceptor)
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
            .build()

    @Provides
    @JvmStatic
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