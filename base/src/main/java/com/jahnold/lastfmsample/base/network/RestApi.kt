package com.jahnold.lastfmsample.base.network

import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchResults
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("?method=album.search")
    fun searchAlbums(@Query("album") search: String): Observable<Response<ApiSearchResults>>

    @GET("?method=album.getInfo")
    fun albumDetails(@Query("mbid") uuid: String): Observable<Response<ApiAlbumDetails>>
}