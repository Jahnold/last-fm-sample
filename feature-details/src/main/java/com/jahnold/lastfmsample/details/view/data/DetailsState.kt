package com.jahnold.lastfmsample.details.view.data

sealed class DetailsState(
    val isLoadingVisible: Boolean,
    val isContentVisible: Boolean,
    val isErrorVisible: Boolean
) {
    object Loading: DetailsState(true, false, false)
    data class Content(val data: DetailsUiModel): DetailsState(false, true, false)
    object Error: DetailsState(false, false, true)
}