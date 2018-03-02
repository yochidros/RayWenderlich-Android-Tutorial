package com.raywenderlich.android.travelwishlist

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_places.view.*

/**
* Created by yochio on 2018/01/26.
*/

class TravelListAdapter(private var context: Context): RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener

    fun setitemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
    override fun getItemCount(): Int = PlaceData.placeList().size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.row_places, parent!!, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val place = PlaceData.placeList()[position]
        holder!!.itemView.placeName.text = place.name
        Picasso.with(context).load(place.getImageResourceId(context))
                .into(holder!!.itemView.placeImage)
        val photo = BitmapFactory.decodeResource(context.resources, place.getImageResourceId(context))
        Palette.from(photo).generate { palette ->  
           val bgColor =  palette.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
            holder.itemView.placeNameHolder.setBackgroundColor(bgColor)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener  {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }
        override fun onClick(v: View?) = itemClickListener.onItemClick(itemView, adapterPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}