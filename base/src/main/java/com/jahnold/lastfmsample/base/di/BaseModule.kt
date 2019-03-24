package com.jahnold.lastfmsample.base.di

import com.jahnold.lastfmsample.base.util.PersistenceHelper
import com.jahnold.lastfmsample.base.util.PersistenceHelperImpl
import com.jahnold.lastfmsample.base.util.SchedulerHelper
import com.jahnold.lastfmsample.base.util.SchedulerHelperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class BaseModule {

    @Singleton
    @Binds
    abstract fun bindSchedulerHelper(schedulerHelperImpl: SchedulerHelperImpl): SchedulerHelper

    @Singleton
    @Binds
    abstract fun bindPrefsHelper(persistenceHelperImpl: PersistenceHelperImpl): PersistenceHelper
}