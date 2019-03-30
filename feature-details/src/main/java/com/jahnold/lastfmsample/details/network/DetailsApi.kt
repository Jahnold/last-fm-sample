package com.jahnold.lastfmsample.details.network

import com.jahnold.lastfmsample.details.network.data.ApiAlbumDetails
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailsApi {

    @GET("?method=album.getInfo")
    fun albumDetails(@Query("mbid") uuid: String): Observable<Response<ApiAlbumDetails>>
}