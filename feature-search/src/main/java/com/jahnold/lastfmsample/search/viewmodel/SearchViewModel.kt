package com.jahnold.lastfmsample.search.viewmodel

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.search.SearchService
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchService: SearchService
): ViewModel() {

    fun search(album: String?) {
        album?.let { searchService.search(it) }
    }
}