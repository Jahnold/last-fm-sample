package com.jahnold.lastfmsample.base.network

import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchResults
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("?method=album.search")
    fun searchAlbums(@Query("album") search: String): Single<Response<ApiSearchResults>>

    @GET("?method=album.getInfo")
    fun getAlbumDetails(@Query("mbid") uuid: String): Single<Response<ApiAlbumDetails>>
}