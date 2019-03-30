@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.base.network

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.details.network.transformer.AlbumDetailsTransformer
import com.jahnold.lastfmsample.list.network.transformer.AlbumSearchTransformer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import retrofit2.Response

class NetworkRepositoryTest {

    val restApi = mock(RestApi::class.java)
    val albumSearchTransformer = spy(com.jahnold.lastfmsample.list.network.transformer.AlbumSearchTransformer::class.java)
    val albumDetailsTransformer = spy(com.jahnold.lastfmsample.details.network.transformer.AlbumDetailsTransformer::class.java)

    val repo = NetworkRepository(restApi, albumSearchTransformer, albumDetailsTransformer)
    val searchSubscriber = TestObserver.create<NetworkRepository.ResultAlbumSearch>()
    val detailsSubscriber = TestObserver.create<NetworkRepository.ResultAlbumDetails>()

    @Test
    fun `searchAlbums - should return error when the network request is not successful`() {

        val errorResponse: Response<com.jahnold.lastfmsample.list.network.data.ApiSearchResults> = Response.error(500, ResponseBody.create(MediaType.parse("json"), "{}"))
        whenever(restApi.searchAlbums(any())).thenReturn(Single.just(errorResponse))

        repo.searchAlbums("").subscribe(searchSubscriber)

        val result = searchSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumSearch.Error::class.java)
    }

    @Test
    fun `searchAlbums - should return success when the network request works`() {

        val response: Response<com.jahnold.lastfmsample.list.network.data.ApiSearchResults> = Response.success(API_SEARCH_RESULTS)
        whenever(restApi.searchAlbums(any())).thenReturn(Single.just(response))

        repo.searchAlbums("").subscribe(searchSubscriber)

        val result = searchSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumSearch.Success::class.java)
    }

    @Test
    fun `searchAlbums - should return error if the result is invalid`() {

        val badData = API_SEARCH_RESULTS.copy(results = null)
        val response: Response<com.jahnold.lastfmsample.list.network.data.ApiSearchResults> = Response.success(badData)
        whenever(restApi.searchAlbums(any())).thenReturn(Single.just(response))

        repo.searchAlbums("").subscribe(searchSubscriber)

        val result = searchSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumSearch.Error::class.java)

    }

    @Test
    fun `getAlbumDetails - should return error when the network request is not successful`() {

        val errorResponse: Response<com.jahnold.lastfmsample.details.network.ApiAlbumDetails> = Response.error(500, ResponseBody.create(MediaType.parse("json"), "{}"))
        whenever(restApi.albumDetails(any())).thenReturn(Single.just(errorResponse))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumDetails.Error::class.java)
    }

    @Test
    fun `getAlbumDetails - should return success when the network request works`() {

        val response: Response<com.jahnold.lastfmsample.details.network.ApiAlbumDetails> = Response.success(API_ALBUM_DETAILS)
        whenever(restApi.albumDetails(any())).thenReturn(Single.just(response))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumDetails.Success::class.java)
    }

    @Test
    fun `getAlbumDetails - should return error if the result is invalid`() {

        val response: Response<com.jahnold.lastfmsample.details.network.ApiAlbumDetails> = Response.success(null)
        whenever(restApi.albumDetails(any())).thenReturn(Single.just(response))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(NetworkRepository.ResultAlbumDetails.Error::class.java)

    }

    companion object {

        val API_SEARCH_RESULTS = com.jahnold.lastfmsample.list.network.data.ApiSearchResults(
            results = com.jahnold.lastfmsample.list.network.data.ApiResults(
                albumMatches = com.jahnold.lastfmsample.list.network.data.ApiAlbums(
                    album = listOf(
                        com.jahnold.lastfmsample.list.network.data.ApiSearchAlbum(
                            uuid = "uuid",
                            name = "name",
                            artist = "artist",
                            images = emptyList()
                        )
                    )
                )
            )
        )

        val API_ALBUM_DETAILS = com.jahnold.lastfmsample.details.network.ApiAlbumDetails(
            album = com.jahnold.lastfmsample.details.network.ApiAlbumDetailsAlbum(
                uuid = "uuid",
                name = "name",
                artist = "artist",
                image = emptyList(),
                tracks = com.jahnold.lastfmsample.details.network.data.ApiTracks(
                    track = emptyList()
                ),
                tags = com.jahnold.lastfmsample.details.network.data.ApiTags(
                    tag = emptyList()
                ),
                wiki = com.jahnold.lastfmsample.details.network.data.ApiWiki(
                    published = "published",
                    summary = "summary"
                )
            )
        )
    }
}