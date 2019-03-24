package com.jahnold.lastfmsample.base.util

import io.reactivex.Scheduler

interface SchedulerHelper {

    fun io(): Scheduler

    fun immediate(): Scheduler

    fun computational(): Scheduler

    fun mainThread(): Scheduler
}