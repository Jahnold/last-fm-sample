package com.jahnold.lastfmsample.base.search

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchService @Inject constructor() {

    private val subject = PublishSubject.create<String>()

    fun search(album: String) {
        subject.onNext(album)
    }

    fun getSearchStream(): Observable<String> {
        return subject
    }
}