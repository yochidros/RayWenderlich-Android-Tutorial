package com.raywenderlich.marsrovers.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.raywenderlich.marsrovers.R
import com.raywenderlich.marsrovers.models.Photo
import com.raywenderlich.marsrovers.models.PhotoRow
import com.raywenderlich.marsrovers.models.RowType

/**
 * Created by yochio on 2018/02/28.
 */


class PhotoAdapter(private var photoList: ArrayList<PhotoRow>) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var filterdPhotos = ArrayList<PhotoRow>()
    private var filtering = false

    override fun getItemCount(): Int {
        if (filtering) {
            return filterdPhotos.size
        } else {
            return photoList.size
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val photoRow : PhotoRow = if (filtering) {
            filterdPhotos[position]
        } else {
            photoList[position]
        }

        if (photoRow.type == RowType.PHOTO) {
            val photo = photoRow.photo
            Glide.with(holder.itemView.context).load(photo?.img_src)
                    .into(holder.getImage(R.id.camera_image))
            photo?.earth_date?.let { holder.setText(R.id.date, it) }
        } else {
            photoRow.header?.let { holder.setText(R.id.header_text, it) }
        }
        setAnimation(holder.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView : View = when (viewType) {
            RowType.PHOTO.ordinal -> layoutInflater.inflate(R.layout.row_item, parent, false)
            else -> layoutInflater.inflate(R.layout.header_item, parent, false)
        }
        return DefaultViewHolder(inflatedView)
    }

    override fun getItemViewType(position: Int): Int =
            if (filtering) {
                filterdPhotos[position].type.ordinal
            } else {
                photoList[position].type.ordinal
            }

    fun filterCamera(camera: String) {
        filtering = true

        val newPhotos = photoList.filter { photo ->
            photo.type == RowType.PHOTO && photo.photo?.camera?.name.equals(camera)
        } as ArrayList<PhotoRow>
        DiffUtil.calculateDiff(PhotoRowDiffCallback(newPhotos, photoList), false).dispatchUpdatesTo(this)
        filterdPhotos = newPhotos
    }


    private fun clearFilter() {
        filtering = false
        filterdPhotos.clear()
    }

    fun updatePhotos(photos: ArrayList<PhotoRow>) {
        DiffUtil.calculateDiff(PhotoRowDiffCallback(photos, photoList), false).dispatchUpdatesTo(this)
        photoList = photos
        clearFilter()
    }

    fun removeRow(row: Int) {
        if (filtering) {
            filterdPhotos.removeAt(row)
        } else {
            photoList.removeAt(row)
        }
        notifyItemRemoved(row)
    }

    private fun setAnimation(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.animation = animation
        }
    }

    class PhotoRowDiffCallback(private val newRows : List<PhotoRow>, private val oldRows: List<PhotoRow>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow.type == newRow.type
        }

        override fun getOldListSize(): Int = oldRows.size

        override fun getNewListSize(): Int = newRows.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow == newRow
        }

    }

}