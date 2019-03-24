package com.jahnold.lastfmsample.list.data

sealed class ListState {
    object Loading: ListState()
    data class Content(val items: List<ListUiModel>): ListState()
    object Error: ListState()
}