package com.jahnold.lastfmsample.base.di

import androidx.lifecycle.ViewModelProvider
import com.jahnold.lastfmsample.base.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory
}