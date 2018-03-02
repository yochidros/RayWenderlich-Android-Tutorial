package com.yochio.bottomsup.ui.feed.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yochio.bottomsup.R
import com.yochio.bottomsup.models.Beer
import com.yochio.bottomsup.ui.feed.holder.BeerHolder

/**
 * Created by yochio on 2018/03/02.
 */


class BeerAdapter : RecyclerView.Adapter<BeerHolder>() {

    private val beers = mutableListOf<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BeerHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_beer, parent, false)
        return BeerHolder(view)
    }

    override fun onBindViewHolder(holder: BeerHolder?, position: Int) {
        val beer = beers[position]
        holder?.run { showBeer(beer) }
    }

    fun clearIfNeeded(page: Int) {
        if (page == 1) {
            beers.clear()
        }
    }

    fun addItems(newBeers: List<Beer>) {
        beers.addAll(newBeers.filter { beer ->
            beer.labels.medium.isNotBlank() || beer.labels.large.isNotBlank()
        })
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return beers.size
    }
}