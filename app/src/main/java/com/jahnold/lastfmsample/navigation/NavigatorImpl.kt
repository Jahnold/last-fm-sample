package com.jahnold.lastfmsample.navigation

import com.jahnold.lastfmsample.base.navigation.Navigator
import com.jahnold.lastfmsample.base.view.BaseFragment
import com.jahnold.lastfmsample.details.view.DetailsFragment
import com.jahnold.lastfmsample.list.view.ListFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {

    override fun getFragmentInstance(destination: Navigator.Fragments): BaseFragment {
        return when (destination) {

            Navigator.Fragments.List -> ListFragment()
            Navigator.Fragments.Details -> DetailsFragment()
            Navigator.Fragments.Search -> TODO()
        }
    }
}