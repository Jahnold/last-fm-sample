package com.jahnold.lastfmsample.list.viewmodel.usecase

import com.jahnold.lastfmsample.base.data.domain.AlbumSearch
import com.jahnold.lastfmsample.base.network.NetworkRepository
import com.jahnold.lastfmsample.base.network.NetworkRepository.ResultAlbumSearch.Error
import com.jahnold.lastfmsample.base.network.NetworkRepository.ResultAlbumSearch.Success
import com.jahnold.lastfmsample.base.search.SearchService
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.base.viewmodel.UseCase0
import com.jahnold.lastfmsample.list.data.ListState
import com.jahnold.lastfmsample.list.data.ListUiModel
import io.reactivex.Observable
import javax.inject.Inject

class ItemsUseCase @Inject constructor(
    private val searchService: SearchService,
    private val networkRepository: NetworkRepository,
    private val transformer: Transformer<AlbumSearch, ListUiModel>,
    private val schedulerHelper: SchedulerHelper
): UseCase0<ListState> {

    override fun getAction(): Observable<ListState> {

        return searchService.getSearchStream()
            .switchMap { search -> networkRepository.searchAlbums(search) }
            .map { result ->
                return@map when (result) {
                    is Success -> mapToContent(result)
                    Error -> ListState.Error
                }
            }
            .startWith(ListState.Loading)
            .subscribeOn(schedulerHelper.io())
            .observeOn(schedulerHelper.mainThread())
    }

    private fun mapToContent(result: Success): ListState {

        return result.result
            .map { transformer.transform(it) }
            .let { ListState.Content(it) }
    }
}