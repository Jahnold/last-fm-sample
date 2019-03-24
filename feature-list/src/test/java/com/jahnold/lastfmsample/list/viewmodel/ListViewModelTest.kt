@file:Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")

package com.jahnold.lastfmsample.list.viewmodel

import com.google.common.truth.Truth.assertThat
import com.jahnold.lastfmsample.base.navigation.NavigationService
import com.jahnold.lastfmsample.base.util.PersistenceHelper
import com.jahnold.lastfmsample.list.data.ListState
import com.jahnold.lastfmsample.list.viewmodel.usecase.ItemsUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ListViewModelTest {

    val itemsUseCase = mock(ItemsUseCase::class.java)
    val persistenceHelper = mock(PersistenceHelper::class.java)
    val navigationService = mock(NavigationService::class.java)

    val viewModel = ListViewModel(itemsUseCase, persistenceHelper, navigationService)
    val subscriber = TestObserver.create<ListState>()

    @Before
    fun setup() {

        whenever(itemsUseCase.getAction()).thenReturn(Observable.just(ListState.Loading))
    }

    @Test
    fun `getListState - should return results from itemsUseCase`() {

        viewModel.getListState().subscribe(subscriber)
        val result = subscriber.values().first()

        assertThat(result).isInstanceOf(ListState.Loading::class.java)
    }

    @Test
    fun `selectItem - should call persistenceHelper & navigationService`() {

        val uuid = "uuid"
        viewModel.selectItem(uuid)

        verify(persistenceHelper).saveAlbumUuid(uuid)
        verify(navigationService).sendNavigationEvent(any())
    }
}