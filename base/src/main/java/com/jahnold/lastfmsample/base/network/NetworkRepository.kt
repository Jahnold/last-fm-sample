package com.jahnold.lastfmsample.base.network

import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchAlbum
import com.jahnold.lastfmsample.base.data.api.ApiSearchResults
import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.transformers.Transformer
import io.reactivex.Observable
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val restApi: RestApi,
    private val albumSearchTransformer: Transformer<ApiSearchAlbum, AlbumSearch>,
    private val albumDetailsTransformer: Transformer<ApiAlbumDetails, AlbumDetails>
) {

    fun searchAlbums(search: String): Observable<ResultAlbumSearch> {

        return restApi
            .searchAlbums(search)
            .map { result ->
                return@map when (result.isSuccessful) {
                    true -> mapToSearchSuccess(result.body())
                    else -> ResultAlbumSearch.Error
                }
            }
            .toObservable()
    }

    private fun mapToSearchSuccess(result: ApiSearchResults?): ResultAlbumSearch {

        val albums = result
            ?.results
            ?.albumMatches
            ?.album
            ?.map { apiAlbum -> apiAlbum.let { albumSearchTransformer.transform(it) } }

        return albums?.let { ResultAlbumSearch.Success(it) } ?: ResultAlbumSearch.Error
    }

    fun getAlbumDetails(uuid: String): Observable<ResultAlbumDetails> {

        return restApi
            .getAlbumDetails(uuid)
            .map { result ->
                return@map when (result.isSuccessful) {
                    true -> mapToDetailsSuccess(result.body())
                    else -> ResultAlbumDetails.Error
                }
            }
            .toObservable()
    }

    private fun mapToDetailsSuccess(result: ApiAlbumDetails?): ResultAlbumDetails {

        val details = result?.let { albumDetailsTransformer.transform(it) }
        return details?.let { ResultAlbumDetails.Success(it) } ?: ResultAlbumDetails.Error
    }

    sealed class ResultAlbumSearch {
        data class Success(val result: List<AlbumSearch>): ResultAlbumSearch()
        object Error: ResultAlbumSearch()
    }

    sealed class ResultAlbumDetails {
        data class Success(val result: AlbumDetails): ResultAlbumDetails()
        object Error: ResultAlbumDetails()
    }
}