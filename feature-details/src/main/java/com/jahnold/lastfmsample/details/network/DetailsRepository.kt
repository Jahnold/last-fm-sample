package com.jahnold.lastfmsample.details.network

import com.jahnold.lastfmsample.details.domain.data.AlbumDetails
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.details.network.data.ApiAlbumDetails
import io.reactivex.Observable
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val api: DetailsApi,
    private val albumDetailsTransformer: Transformer<ApiAlbumDetails, AlbumDetails>
){

    fun getAlbumDetails(uuid: String): Observable<ResultAlbumDetails> {

        return api
            .albumDetails(uuid)
            .map { result ->
                return@map when (result.isSuccessful) {
                    true -> mapToDetailsSuccess(result.body())
                    else -> ResultAlbumDetails.Error
                }
            }
    }

    private fun mapToDetailsSuccess(result: ApiAlbumDetails?): ResultAlbumDetails {

        val details = result?.let { albumDetailsTransformer.transform(it) }
        return details?.let { ResultAlbumDetails.Success(it) } ?: ResultAlbumDetails.Error
    }

    sealed class ResultAlbumDetails {
        data class Success(val result: AlbumDetails): ResultAlbumDetails()
        object Error: ResultAlbumDetails()
    }
}