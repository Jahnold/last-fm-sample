package com.jahnold.lastfmsample.base.network.data

import com.google.gson.annotations.SerializedName

data class ApiImage(

    @SerializedName("#text")
    val url: String?,
    val size: String?
)