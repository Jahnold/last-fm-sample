@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.data.ImageSize
import com.jahnold.lastfmsample.base.search.SearchService
import com.jahnold.lastfmsample.basetest.TestSchedulerHelper
import com.jahnold.lastfmsample.list.domain.data.AlbumSearch
import com.jahnold.lastfmsample.list.network.ListRepository
import com.jahnold.lastfmsample.list.view.data.ListState
import com.jahnold.lastfmsample.list.view.data.ListUiModelTransformer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

class ItemsUseCaseTest {

    val searchService = mock(SearchService::class.java)
    val networkRepository = mock(ListRepository::class.java)
    val transformer = spy(ListUiModelTransformer::class.java)
    val schedulerHelper = TestSchedulerHelper()

    val useCase = ItemsUseCase(searchService, networkRepository, transformer, schedulerHelper)

    val subscriber = TestObserver.create<ListState>()
    val searchBroadcast = PublishSubject.create<String>()
    val resultBroadcast = PublishSubject.create<ListRepository.ResultAlbumSearch>()

    @Before
    fun setUp() {

        whenever(searchService.getSearchStream()).thenReturn(searchBroadcast)
        whenever(networkRepository.searchAlbums(any())).thenReturn(resultBroadcast)
    }

    @Test
    fun `should start with Loading`() {

        useCase.getAction().subscribe(subscriber)

        val result = subscriber.values().first()

        assertThat(result).isInstanceOf(ListState.Loading::class.java)
    }

    @Test
    fun `should pass search value to network repo`() {

        useCase.getAction().subscribe(subscriber)
        val search = "album"
        searchBroadcast.onNext(search)

        verify(networkRepository).searchAlbums(search)
    }

    @Test
    fun `should return Error if network errors`() {

        useCase.getAction().subscribe(subscriber)
        searchBroadcast.onNext("")
        resultBroadcast.onNext(ListRepository.ResultAlbumSearch.Error)

        val result = subscriber.values()

        assertThat(result).hasSize(2)
        assertThat(result[0]).isInstanceOf(ListState.Loading::class.java)
        assertThat(result[1]).isInstanceOf(ListState.Error::class.java)
    }

    @Test
    fun `should transform results on success`() {

        useCase.getAction().subscribe(subscriber)
        searchBroadcast.onNext("")
        resultBroadcast.onNext(ListRepository.ResultAlbumSearch.Success(listOf(ALBUM)))

        verify(transformer).transform(ALBUM)
    }

    @Test
    fun `should return Content on success`() {

        useCase.getAction().subscribe(subscriber)
        searchBroadcast.onNext("")
        resultBroadcast.onNext(ListRepository.ResultAlbumSearch.Success(listOf(ALBUM)))

        val result = subscriber.values()

        assertThat(result).hasSize(2)
        assertThat(result[0]).isInstanceOf(ListState.Loading::class.java)
        assertThat(result[1]).isInstanceOf(ListState.Content::class.java)
    }

    companion object {

        const val UUID = "uuid"
        const val NAME = "name"
        const val ARTIST = "artist"

        const val IMAGE_URL1 = "url1"
        const val IMAGE_URL2 = "url2"

        val images = mapOf(
            ImageSize.MEDIUM to IMAGE_URL1,
            ImageSize.EXTRA_LARGE to IMAGE_URL2
        )

        val ALBUM = AlbumSearch(
            uuid = UUID,
            name = NAME,
            artist = ARTIST,
            images = images
        )
    }
}