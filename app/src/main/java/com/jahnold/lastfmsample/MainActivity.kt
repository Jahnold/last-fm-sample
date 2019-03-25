package com.jahnold.lastfmsample

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jahnold.lastfmsample.base.navigation.Navigator
import com.jahnold.lastfmsample.base.view.BaseActivity
import com.jahnold.lastfmsample.base.viewmodel.ViewModelFactory
import com.jahnold.lastfmsample.search.view.SearchFragment
import com.jahnold.lastfmsample.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity: BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var navigator: Navigator

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .apply {
                    replace(R.id.layout_container, SearchFragment())
                    commit()
                }
        }

        subscribeToNavigationEvents()
    }

    private fun subscribeToNavigationEvents() {

        viewModel
            .getNavigationEvents()
            .subscribe(
                { navigationToFragment(it) },
                { it.printStackTrace() }
            )
            .track()
    }

    private fun navigationToFragment(destination: Navigator.Fragments) {

        val fragment = navigator.getFragmentInstance(destination)
        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.layout_container, fragment)
                addToBackStack(null)
                commit()
            }
    }

    override fun onBackPressed() {

        supportFragmentManager.popBackStack()
    }
}
