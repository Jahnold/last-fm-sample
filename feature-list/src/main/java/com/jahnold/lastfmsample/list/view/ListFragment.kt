package com.jahnold.lastfmsample.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jahnold.lastfmsample.base.extension.bindView
import com.jahnold.lastfmsample.base.view.BaseFragment
import com.jahnold.lastfmsample.list.R
import com.jahnold.lastfmsample.list.data.ListState
import com.jahnold.lastfmsample.list.data.ListUiModel
import com.jahnold.lastfmsample.list.viewmodel.ListViewModel
import javax.inject.Inject

class ListFragment: BaseFragment() {

    @Inject lateinit var adapter: ListAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ListViewModel

    private val recycler: RecyclerView by bindView(R.id.recycler)
    private val loading: ProgressBar by bindView(R.id.list_loading)
    private val error: TextView by bindView(R.id.list_error)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribeToData()
        subscribeToItemClicks()
    }

    private fun initRecycler() {

        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.adapter = adapter
    }

    private fun subscribeToData() {

        viewModel.getListState()
            .subscribe(
                { result ->
                    when (result) {
                        ListState.Loading -> showLoading()
                        is ListState.Content -> showContent(result.items)
                        ListState.Error -> showError()
                    }
                },
                { it.printStackTrace() }
            )
            .track()
    }

    private fun showLoading() {
        loading.isVisible = true
        recycler.isVisible = false
        error.isVisible = false
    }

    private fun showContent(data: List<ListUiModel>) {
        loading.isVisible = false
        recycler.isVisible = true
        error.isVisible = false

        adapter.setData(data)
    }

    private fun showError() {
        loading.isVisible = false
        recycler.isVisible = false
        error.isVisible = true
    }

    private fun subscribeToItemClicks() {

        adapter.getItemClicks()
            .subscribe(
                { uuid -> viewModel.selectItem(uuid)},
                { it.printStackTrace() }
            )
            .track()
    }
}