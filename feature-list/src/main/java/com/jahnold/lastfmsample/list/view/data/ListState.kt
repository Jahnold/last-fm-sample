package com.jahnold.lastfmsample.list.view.data

sealed class ListState {
    object Loading: ListState()
    data class Content(val items: List<ListUiModel>): ListState()
    object Error: ListState()
}