package com.jahnold.lastfmsample.base.search

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchService @Inject constructor() {

    private val subject = BehaviorSubject.create<String>()

    fun search(album: String) {
        subject.onNext(album)
    }

    fun getSearchStream(): Observable<String> {
        return subject
    }
}