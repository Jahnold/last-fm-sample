package com.jahnold.lastfmsample.list.viewmodel

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.navigation.NavigationService
import com.jahnold.lastfmsample.base.navigation.Navigator
import com.jahnold.lastfmsample.base.util.PersistenceHelper
import com.jahnold.lastfmsample.list.data.ListState
import com.jahnold.lastfmsample.list.viewmodel.usecase.ItemsUseCase
import io.reactivex.Observable
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val itemsUseCase: ItemsUseCase,
    private val persistenceHelper: PersistenceHelper,
    private val navigationService: NavigationService
): ViewModel() {

    fun getListState(): Observable<ListState> {
        return itemsUseCase.getAction()
    }

    fun selectItem(uuid: String) {
        persistenceHelper.saveAlbumUuid(uuid)
        navigationService.sendNavigationEvent(Navigator.Fragments.Details)
    }
}