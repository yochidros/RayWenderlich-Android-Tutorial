package com.raywenderlich.galacticon.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.galacticon.R
import com.raywenderlich.galacticon.extensions.inflate
import com.raywenderlich.galacticon.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

/**
 * Created by yochio on 2018/02/28.
 */

class RecyclerAdapter(private val photos: ArrayList<Photo>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoHolder {
        val inflatedView = parent!!.inflate(R.layout.recyclerview_item_row, false)
        return PhotoHolder(inflatedView)
    }

    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var photo: Photo? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, PhotoActivity::class.java)
            showPhotoIntent.putExtra(PHOTO_KEY, photo)
            context.startActivity(showPhotoIntent)
        }

        fun bindPhoto(photo: Photo) {
            this.photo = photo
            Picasso.with(view.context).load(photo.url).into(view.itemImage)
            view.itemDate.text = photo.humanDate
            view.itemDescription.text = photo.explanation
        }

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }
    }
}

