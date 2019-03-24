package com.jahnold.lastfmsample.base.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerHelperImpl @Inject constructor() : SchedulerHelper {

    override fun io(): Scheduler = Schedulers.io()

    override fun immediate(): Scheduler = Schedulers.trampoline()

    override fun computational(): Scheduler = Schedulers.computation()

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}