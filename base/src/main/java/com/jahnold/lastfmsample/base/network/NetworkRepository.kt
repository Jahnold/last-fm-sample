package com.jahnold.lastfmsample.base.network

import com.jahnold.lastfmsample.base.data.api.ApiAlbumDetails
import com.jahnold.lastfmsample.base.data.api.ApiSearchAlbum
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

        val result = restApi
            .searchAlbums(search)
            .toObservable()
            .publish()
            .autoConnect()

        val happy = result
            .filter { it.isSuccessful }
            .map { it.body()?.results?.albumMatches?.album }
            .map { apiAlbums -> apiAlbums.map { albumSearchTransformer.transform(it) } }
            .map { albums -> ResultAlbumSearch.Success(albums) }

        val sad = result
            .filter { !it.isSuccessful }
            .map { ResultAlbumSearch.Error }

        return Observable.merge(happy, sad).take(1)
    }

    fun getAlbumDetails(uuid: String): Observable<ResultAlbumDetails> {

        val result = restApi
            .getAlbumDetails(uuid)
            .toObservable()
            .publish()
            .autoConnect()

        val happy = result
            .filter { it.isSuccessful }
            .map { it.body() }
            .map { apiAlbum -> albumDetailsTransformer.transform(apiAlbum) }
            .map { album -> ResultAlbumDetails.Success(album) }

        val sad = result
            .filter { !it.isSuccessful }
            .map { ResultAlbumDetails.Error }

        return Observable.merge(happy, sad).take(1)
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