package com.jahnold.lastfmsample.list.di

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.di.BaseModule
import com.jahnold.lastfmsample.base.di.NetworkModule
import com.jahnold.lastfmsample.base.di.ViewModelKey
import com.jahnold.lastfmsample.base.di.ViewModelModule
import com.jahnold.lastfmsample.list.view.ListFragment
import com.jahnold.lastfmsample.list.viewmodel.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    ViewModelModule::class,
    BaseModule::class,
    ListDataModule::class,
    NetworkModule::class
])
abstract class ListModule {

    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(viewModel: ListViewModel): ViewModel
}