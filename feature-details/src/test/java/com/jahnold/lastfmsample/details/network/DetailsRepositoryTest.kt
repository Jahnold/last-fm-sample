@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.details.network

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.details.network.data.*
import com.jahnold.lastfmsample.details.network.transformer.AlbumDetailsTransformer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class DetailsRepositoryTest {

    val api = Mockito.mock(DetailsApi::class.java)
    val albumDetailsTransformer = Mockito.spy(AlbumDetailsTransformer::class.java)

    val repo = DetailsRepository(api, albumDetailsTransformer)
    val detailsSubscriber = TestObserver.create<DetailsRepository.ResultAlbumDetails>()

    @Test
    fun `getAlbumDetails - should return error when the network request is not successful`() {

        val errorResponse: Response<ApiAlbumDetails> = Response.error(500, ResponseBody.create(MediaType.parse("json"), "{}"))
        whenever(api.albumDetails(any())).thenReturn(Observable.just(errorResponse))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(DetailsRepository.ResultAlbumDetails.Error::class.java)
    }

    @Test
    fun `getAlbumDetails - should return success when the network request works`() {

        val response: Response<ApiAlbumDetails> = Response.success(API_ALBUM_DETAILS)
        whenever(api.albumDetails(any())).thenReturn(Observable.just(response))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(DetailsRepository.ResultAlbumDetails.Success::class.java)
    }

    @Test
    fun `getAlbumDetails - should return error if the result is invalid`() {

        val response: Response<ApiAlbumDetails> = Response.success(null)
        whenever(api.albumDetails(any())).thenReturn(Observable.just(response))

        repo.getAlbumDetails("").subscribe(detailsSubscriber)

        val result = detailsSubscriber.values().first()

        assertThat(result).isInstanceOf(DetailsRepository.ResultAlbumDetails.Error::class.java)
    }

    companion object {

        val API_ALBUM_DETAILS = ApiAlbumDetails(
            album = ApiAlbumDetailsAlbum(
                uuid = "uuid",
                name = "name",
                artist = "artist",
                image = emptyList(),
                tracks = ApiTracks(
                    track = emptyList()
                ),
                tags = ApiTags(
                    tag = emptyList()
                ),
                wiki = ApiWiki(
                    published = "published",
                    summary = "summary"
                )
            )
        )
    }
}