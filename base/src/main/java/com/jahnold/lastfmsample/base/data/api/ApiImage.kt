package com.jahnold.lastfmsample.base.data.api

import com.google.gson.annotations.SerializedName

data class ApiImage(

    @SerializedName("#text")
    val url: String?,
    val size: String?
)