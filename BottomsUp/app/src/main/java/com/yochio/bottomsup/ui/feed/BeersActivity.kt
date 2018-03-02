package com.yochio.bottomsup.ui.feed

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.yochio.bottomsup.R
import com.yochio.bottomsup.models.BeerLabel
import com.yochio.bottomsup.ui.feed.Adapter.BeerAdapter
import com.yochio.bottomsup.util.getViewModel
import com.yochio.bottomsup.util.subscribe
import com.yochio.bottomsup.viewModels.BeersViewModel
import kotlinx.android.synthetic.main.activity_beers.*

class BeersActivity : AppCompatActivity() {

    private val viewModel by lazy {
        getViewModel<BeersViewModel>()
    }

    private val adapter = BeerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers)

        initializeUI()

        viewModel.getBeers()

        viewModel.errorData.subscribe(this, this::setErrorVisibility)
        viewModel.loadingData.subscribe(this, this::showLoading)
        viewModel.pageData.subscribe(this, adapter::clearIfNeeded)
        viewModel.beerData.subscribe(this, adapter::addItems)
    }

    private fun initializeUI() {
        beersList.layoutManager = GridLayoutManager(this, 2)
        beersList.itemAnimator = DefaultItemAnimator()
        beersList.adapter = adapter

        pullToRefresh.setOnRefreshListener(viewModel::onRefresh)
    }

    private fun showLoading(isLoading: Boolean) {
        pullToRefresh.isRefreshing = isLoading
    }

    private fun setErrorVisibility(shouldShow: Boolean) {
        errorView.visibility = if (shouldShow) View.VISIBLE else View.GONE
        beersList.visibility = if (!shouldShow) View.VISIBLE else View.GONE
    }
}
