package com.jahnold.lastfmsample.details.di

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.base.di.BaseModule
import com.jahnold.lastfmsample.base.di.NetworkModule
import com.jahnold.lastfmsample.base.di.ViewModelKey
import com.jahnold.lastfmsample.base.di.ViewModelModule
import com.jahnold.lastfmsample.details.domain.DetailsViewModel
import com.jahnold.lastfmsample.details.view.DetailsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    ViewModelModule::class,
    BaseModule::class,
    DetailsDataModule::class,
    NetworkModule::class
])
abstract class DetailsModule {

    @ContributesAndroidInjector
    abstract fun bindDetailsFragment(): DetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}