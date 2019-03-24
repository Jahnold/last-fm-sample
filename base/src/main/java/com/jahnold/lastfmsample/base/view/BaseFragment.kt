package com.jahnold.lastfmsample.base.view

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment: DaggerFragment() {

    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        val activity = activity
        if (isRemoving || parentFragment != null && parentFragment!!.isRemoving || activity != null && activity.isFinishing) {
            disposables.clear()
        }
        super.onDestroy()
    }

    fun Disposable.track(): Boolean = disposables.add(this)
}