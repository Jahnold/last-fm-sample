package com.jahnold.lastfmsample.base.view

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}