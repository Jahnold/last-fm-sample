@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.details.domain

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.util.PersistenceHelper
import com.jahnold.lastfmsample.details.view.data.DetailsState
import com.jahnold.lastfmsample.details.domain.usecase.ItemUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DetailsViewModelTest {

    val persistenceHelper = Mockito.mock(PersistenceHelper::class.java)
    val itemUseCase = Mockito.mock(ItemUseCase::class.java)

    val viewModel = DetailsViewModel(persistenceHelper, itemUseCase)
    val subscriber = TestObserver.create<DetailsState>()

    @Before
    fun setup() {
        whenever(persistenceHelper.getAlbumUuid()).thenReturn(UUID)
        whenever(itemUseCase.getAction(any())).thenReturn(Observable.just(DetailsState.Loading))
    }

    @Test
    fun `should get uuid`() {

        viewModel.getDetailsData().subscribe(subscriber)
        verify(persistenceHelper).getAlbumUuid()
    }

    @Test
    fun `should pass uuid to use Case if valid`() {

        viewModel.getDetailsData().subscribe(subscriber)
        verify(itemUseCase).getAction(UUID)
    }

    @Test
    fun `should return Error if uuid is null`() {

        whenever(persistenceHelper.getAlbumUuid()).thenReturn(null)
        viewModel.getDetailsData().subscribe(subscriber)

        val result = subscriber.values().first()

        assertThat(result).isInstanceOf(DetailsState.Error::class.java)
    }

    @Test
    fun `should return Error if uuid is empty`() {

        whenever(persistenceHelper.getAlbumUuid()).thenReturn("")
        viewModel.getDetailsData().subscribe(subscriber)

        val result = subscriber.values().first()

        assertThat(result).isInstanceOf(DetailsState.Error::class.java)
    }

    companion object {
        const val UUID = "uuid"
    }
}