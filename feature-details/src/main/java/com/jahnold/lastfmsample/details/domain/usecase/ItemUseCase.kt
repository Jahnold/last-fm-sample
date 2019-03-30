package com.jahnold.lastfmsample.details.domain.usecase

import com.jahnold.lastfmsample.details.domain.data.AlbumDetails
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.base.viewmodel.UseCase1
import com.jahnold.lastfmsample.details.view.data.DetailsState
import com.jahnold.lastfmsample.details.view.data.DetailsUiModel
import com.jahnold.lastfmsample.details.network.DetailsRepository
import com.jahnold.lastfmsample.details.network.DetailsRepository.ResultAlbumDetails
import io.reactivex.Observable
import javax.inject.Inject

class ItemUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val schedulerHelper: SchedulerHelper,
    private val transformer: Transformer<AlbumDetails, DetailsUiModel>
): UseCase1<String, DetailsState> {

    override fun getAction(input: String): Observable<DetailsState> {

        return detailsRepository
            .getAlbumDetails(input)
            .map { result ->
                return@map when (result) {
                    is ResultAlbumDetails.Success -> mapToContent(result)
                    ResultAlbumDetails.Error -> DetailsState.Error
                }
            }
            .startWith(DetailsState.Loading)
            .subscribeOn(schedulerHelper.io())
            .observeOn(schedulerHelper.mainThread())
    }

    private fun mapToContent(result: ResultAlbumDetails.Success): DetailsState {

        val album = transformer.transform(result.result)
        return DetailsState.Content(album)
    }
}