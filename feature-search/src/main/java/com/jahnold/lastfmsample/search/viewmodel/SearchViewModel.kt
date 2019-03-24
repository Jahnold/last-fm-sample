package com.jahnold.lastfmsample.search.viewmodel

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.navigation.NavigationService
import com.jahnold.lastfmsample.base.navigation.Navigator
import com.jahnold.lastfmsample.base.search.SearchService
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchService: SearchService,
    private val navigationService: NavigationService
): ViewModel() {

    fun search(album: String?) {
        album?.let {
            searchService.search(it)
            navigationService.sendNavigationEvent(Navigator.Fragments.List)
        }
    }
}