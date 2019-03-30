@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.details.domain.usecase

import com.google.common.truth.Truth
import com.jahnold.lastfmsample.details.domain.data.AlbumDetails
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.details.view.data.DetailsState
import com.jahnold.lastfmsample.details.view.data.DetailsUiModelTransformer
import com.jahnold.lastfmsample.details.network.DetailsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ItemUseCaseTest {

    val detailsRepository = Mockito.mock(DetailsRepository::class.java)
    val schedulerHelper = Mockito.mock(SchedulerHelper::class.java)
    val transformer = Mockito.spy(DetailsUiModelTransformer::class.java)

    val useCase = ItemUseCase(detailsRepository, schedulerHelper, transformer)
    val subscriber = TestObserver.create<DetailsState>()
    val broadcast = PublishSubject.create<DetailsRepository.ResultAlbumDetails>()

    @Before
    fun setup() {
        whenever(detailsRepository.getAlbumDetails(any())).thenReturn(broadcast)
        whenever(schedulerHelper.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulerHelper.mainThread()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun `should start with Loading`() {

        useCase.getAction(UUID).subscribe(subscriber)

        val result = subscriber.values().first()

        Truth.assertThat(result).isInstanceOf(DetailsState.Loading::class.java)
    }

    @Test
    fun `should pass uuid value to network repo`() {

        useCase.getAction(UUID).subscribe(subscriber)
        verify(detailsRepository).getAlbumDetails(UUID)
    }

    @Test
    fun `should return Error if network errors`() {

        useCase.getAction(UUID).subscribe(subscriber)
        broadcast.onNext(DetailsRepository.ResultAlbumDetails.Error)

        val result = subscriber.values()

        Truth.assertThat(result).hasSize(2)
        Truth.assertThat(result[0]).isInstanceOf(DetailsState.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(DetailsState.Error::class.java)
    }

    @Test
    fun `should transform results on success`() {

        useCase.getAction(UUID).subscribe(subscriber)
        broadcast.onNext(DetailsRepository.ResultAlbumDetails.Success(ALBUM))

        verify(transformer).transform(ALBUM)
    }

    @Test
    fun `should return Content on success`() {

        useCase.getAction(UUID).subscribe(subscriber)
        broadcast.onNext(DetailsRepository.ResultAlbumDetails.Success(ALBUM))

        val result = subscriber.values()

        Truth.assertThat(result).hasSize(2)
        Truth.assertThat(result[0]).isInstanceOf(DetailsState.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(DetailsState.Content::class.java)
    }

    companion object {

        const val UUID = "uuid"

        val ALBUM = AlbumDetails(
            uuid = UUID,
            name = "name",
            artist = "artist",
            images = emptyMap(),
            tracks = emptyList(),
            tags = emptyList(),
            published = "published",
            summary = "summary"
        )
    }
}