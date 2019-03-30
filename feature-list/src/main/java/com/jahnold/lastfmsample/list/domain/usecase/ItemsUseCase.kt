package com.jahnold.lastfmsample.list.domain.usecase

import com.jahnold.lastfmsample.base.search.SearchService
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.base.viewmodel.UseCase0
import com.jahnold.lastfmsample.list.domain.data.AlbumSearch
import com.jahnold.lastfmsample.list.network.ListRepository
import com.jahnold.lastfmsample.list.network.ListRepository.ResultAlbumSearch
import com.jahnold.lastfmsample.list.view.data.ListState
import com.jahnold.lastfmsample.list.view.data.ListUiModel
import io.reactivex.Observable
import javax.inject.Inject

class ItemsUseCase @Inject constructor(
    private val searchService: SearchService,
    private val listRepository: ListRepository,
    private val transformer: Transformer<AlbumSearch, ListUiModel>,
    private val schedulerHelper: SchedulerHelper
): UseCase0<ListState> {

    override fun getAction(): Observable<ListState> {

        return searchService.getSearchStream()
            .switchMap { search -> listRepository.searchAlbums(search) }
            .map { result ->
                return@map when (result) {
                    is ResultAlbumSearch.Success -> mapToContent(result)
                    ResultAlbumSearch.Error -> ListState.Error
                }
            }
            .startWith(ListState.Loading)
            .subscribeOn(schedulerHelper.io())
            .observeOn(schedulerHelper.mainThread())
    }

    private fun mapToContent(result: ResultAlbumSearch.Success): ListState {

        return result.result
            .map { transformer.transform(it) }
            .let { ListState.Content(it) }
    }
}