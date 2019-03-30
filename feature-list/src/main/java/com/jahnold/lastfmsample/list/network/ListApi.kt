package com.jahnold.lastfmsample.list.network

import com.jahnold.lastfmsample.list.network.data.ApiSearchResults
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApi {

    @GET("?method=album.search")
    fun searchAlbums(@Query("album") search: String): Observable<Response<ApiSearchResults>>
}