package com.jahnold.lastfmsample.di

import com.jahnold.lastfmsample.base.navigation.Navigator
import com.jahnold.lastfmsample.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @Provides
    @JvmStatic
    fun providesNavigator(navigator: NavigatorImpl): Navigator = navigator
}