@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.list.viewmodel.usecase

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.data.domain.ImageSize
import com.jahnold.lastfmsample.base.network.NetworkRepository
import com.jahnold.lastfmsample.base.network.NetworkRepository.ResultAlbumSearch.Error
import com.jahnold.lastfmsample.base.network.NetworkRepository.ResultAlbumSearch.Success
import com.jahnold.lastfmsample.base.search.SearchService
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.list.data.ListState
import com.jahnold.lastfmsample.list.data.ListUiModelTransformer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

class ItemsUseCaseTest {

    val searchService = mock(SearchService::class.java)
    val networkRepository = mock(NetworkRepository::class.java)
    val transformer = spy(ListUiModelTransformer::class.java)
    val schedulerHelper = mock(SchedulerHelper::class.java)

    val useCase = ItemsUseCase(searchService, networkRepository, transformer, schedulerHelper)

    val subscriber = TestObserver.create<ListState>()
    val searchBroadcast = PublishSubject.create<String>()
    val resultBroadcast = PublishSubject.create<NetworkRepository.ResultAlbumSearch>()

    @Before
    fun setUp() {

        whenever(searchService.getSearchStream()).thenReturn(searchBroadcast)
        whenever(networkRepository.searchAlbums(any())).thenReturn(resultBroadcast)
        whenever(schedulerHelper.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulerHelper.mainThread()).thenReturn(Schedulers.trampoline())
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
        resultBroadcast.onNext(Error)

        val result = subscriber.values()

        assertThat(result).hasSize(2)
        assertThat(result[0]).isInstanceOf(ListState.Loading::class.java)
        assertThat(result[1]).isInstanceOf(ListState.Error::class.java)
    }

    @Test
    fun `should transform results on success`() {

        useCase.getAction().subscribe(subscriber)
        searchBroadcast.onNext("")
        resultBroadcast.onNext(Success(listOf(ALBUM)))

        verify(transformer).transform(ALBUM)
    }

    @Test
    fun `should return Content on success`() {

        useCase.getAction().subscribe(subscriber)
        searchBroadcast.onNext("")
        resultBroadcast.onNext(Success(listOf(ALBUM)))

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