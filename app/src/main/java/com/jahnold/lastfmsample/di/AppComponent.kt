package com.jahnold.lastfmsample.di

import com.jahnold.lastfmsample.App
import com.jahnold.lastfmsample.details.di.DetailsModule
import com.jahnold.lastfmsample.list.di.ListModule
import com.jahnold.lastfmsample.search.di.SearchModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        MainModule::class,
        ActivityModule::class,
        SearchModule::class,
        ListModule::class,
        DetailsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}