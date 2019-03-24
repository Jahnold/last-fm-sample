package com.jahnold.lastfmsample.search.di

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.di.BaseModule
import com.jahnold.lastfmsample.base.di.ViewModelKey
import com.jahnold.lastfmsample.base.di.ViewModelModule
import com.jahnold.lastfmsample.search.view.SearchFragment
import com.jahnold.lastfmsample.search.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    ViewModelModule::class,
    BaseModule::class
])
abstract class SearchModule {

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindListViewModel(viewModel: SearchViewModel): ViewModel
}