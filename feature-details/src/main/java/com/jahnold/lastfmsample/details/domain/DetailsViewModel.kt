package com.jahnold.lastfmsample.details.domain

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.util.PersistenceHelper
import com.jahnold.lastfmsample.details.view.data.DetailsState
import com.jahnold.lastfmsample.details.domain.usecase.ItemUseCase
import io.reactivex.Observable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val persistenceHelper: PersistenceHelper,
    private val itemUseCase: ItemUseCase
): ViewModel() {

    fun getDetailsData(): Observable<DetailsState> {

        val uuid = persistenceHelper.getAlbumUuid()

        return when (!uuid.isNullOrEmpty()) {
            true -> itemUseCase.getAction(uuid)
            else -> Observable.just(DetailsState.Error)
        }
    }
}