package com.jahnold.lastfmsample.base.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    @JvmStatic
    internal fun providesLoggingInterceptor(): HttpLoggingInterceptor? =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else null

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttp(
        queryParamsInterceptor: QueryParamsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor?
    ): OkHttpClient {

        val builder =  OkHttpClient.Builder()
            .addInterceptor(queryParamsInterceptor)

        if (loggingInterceptor != null) {
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    @JvmStatic
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
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