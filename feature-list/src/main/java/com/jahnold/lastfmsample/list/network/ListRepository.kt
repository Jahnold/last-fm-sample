package com.jahnold.lastfmsample.list.network

import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.list.domain.data.AlbumSearch
import com.jahnold.lastfmsample.list.network.data.ApiSearchAlbum
import com.jahnold.lastfmsample.list.network.data.ApiSearchResults
import io.reactivex.Observable
import javax.inject.Inject

class ListRepository @Inject constructor(
    private val api: ListApi,
    private val albumSearchTransformer: Transformer<ApiSearchAlbum, AlbumSearch>
) {

    fun searchAlbums(search: String): Observable<ResultAlbumSearch> {

        return api
            .searchAlbums(search)
            .map { result ->
                return@map when (result.isSuccessful) {
                    true -> mapToSearchSuccess(result.body())
                    else -> ResultAlbumSearch.Error
                }
            }
    }

    private fun mapToSearchSuccess(result: ApiSearchResults?): ResultAlbumSearch {

        val albums = result
            ?.results
            ?.albumMatches
            ?.album
            ?.map { apiAlbum -> apiAlbum.let { albumSearchTransformer.transform(it) } }

        return albums?.let { ResultAlbumSearch.Success(it) } ?: ResultAlbumSearch.Error
    }

    sealed class ResultAlbumSearch {
        data class Success(val result: List<com.jahnold.lastfmsample.list.domain.data.AlbumSearch>): ResultAlbumSearch()
        object Error: ResultAlbumSearch()
    }
}