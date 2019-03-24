package com.jahnold.lastfmsample.di

import androidx.lifecycle.ViewModel
import com.jahnold.lastfmsample.MainActivity
import com.jahnold.lastfmsample.base.di.ViewModelKey
import com.jahnold.lastfmsample.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}