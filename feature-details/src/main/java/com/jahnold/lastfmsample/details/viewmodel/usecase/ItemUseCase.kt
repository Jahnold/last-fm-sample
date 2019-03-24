package com.jahnold.lastfmsample.details.viewmodel.usecase

import com.jahnold.lastfmsample.base.data.domain.AlbumDetails
import com.jahnold.lastfmsample.base.network.NetworkRepository
import com.jahnold.lastfmsample.base.transformers.Transformer
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.base.viewmodel.UseCase1
import com.jahnold.lastfmsample.details.data.DetailsState
import com.jahnold.lastfmsample.details.data.DetailsUiModel
import io.reactivex.Observable
import javax.inject.Inject

class ItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val schedulerHelper: SchedulerHelper,
    private val transformer: Transformer<AlbumDetails, DetailsUiModel>
): UseCase1<String, DetailsState> {

    override fun getAction(input: String): Observable<DetailsState> {

        return networkRepository
            .getAlbumDetails(input)
            .map { result ->
                return@map when (result) {
                    is NetworkRepository.ResultAlbumDetails.Success -> DetailsState.Success(transformer.transform(result.result))
                    NetworkRepository.ResultAlbumDetails.Error -> DetailsState.Error
                }
            }
            .startWith{ DetailsState.Loading }
            .subscribeOn(schedulerHelper.io())
            .observeOn(schedulerHelper.mainThread())
    }
}