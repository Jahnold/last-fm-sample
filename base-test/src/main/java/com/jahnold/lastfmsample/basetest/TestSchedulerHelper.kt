package com.jahnold.lastfmsample.basetest

import com.jahnold.lastfmsample.base.util.SchedulerHelper
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerHelper: SchedulerHelper {

    override fun io(): Scheduler = Schedulers.trampoline()
    override fun immediate(): Scheduler = Schedulers.trampoline()
    override fun computational(): Scheduler = Schedulers.trampoline()
    override fun mainThread(): Scheduler = Schedulers.trampoline()
}