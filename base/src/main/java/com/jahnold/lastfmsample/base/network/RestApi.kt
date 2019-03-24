package com.jahnold.lastfmsample.base.network

import com.jahnold.lastfmsample.base.data.api.ApiComment
import com.jahnold.lastfmsample.base.data.api.ApiPost
import com.jahnold.lastfmsample.base.data.api.ApiUser
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {

    @GET("posts")
    fun getPosts(): Single<List<ApiPost>>

    @GET("users")
    fun getUsers(): Single<List<ApiUser>>

    @GET("comments")
    fun getComments(): Single<List<ApiComment>>
}