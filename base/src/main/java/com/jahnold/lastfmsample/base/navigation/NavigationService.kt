package com.jahnold.lastfmsample.base.navigation

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationService @Inject constructor() {

    private val subject = PublishSubject.create<Navigator.Fragments>()

    fun getNavigationBroadcast(): Observable<Navigator.Fragments> {
        return subject
    }

    fun sendNavigationEvent(destination: Navigator.Fragments) {
        subject.onNext(destination)
    }
}