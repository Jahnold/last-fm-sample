package com.jahnold.lastfmsample.base.navigation

import com.jahnold.lastfmsample.base.view.BaseFragment

interface Navigator {

    sealed class Fragments {
        object List: Fragments()
        object Details: Fragments()
        object Search: Fragments()
    }

    fun getFragmentInstance(destination: Fragments): BaseFragment
}