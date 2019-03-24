package com.jahnold.lastfmsample.base.view

import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity: DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    fun Disposable.track(): Boolean = disposables.add(this)
}