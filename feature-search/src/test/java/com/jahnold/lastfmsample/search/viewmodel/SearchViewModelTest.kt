@file:Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")

package com.jahnold.lastfmsample.search.viewmodel

import com.jahnold.lastfmsample.base.search.SearchService
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test
import org.mockito.Mockito

class SearchViewModelTest {

    val searchService = Mockito.mock(SearchService::class.java)
    val viewModel = SearchViewModel(searchService)

    @Test
    fun `should not pass null searches to service`() {

        viewModel.search(null)
        verifyZeroInteractions(searchService)
    }

    @Test
    fun `should pass valid search input to service`() {

        val query = "album"

        viewModel.search(query)
        verify(searchService).search(query)
    }
}