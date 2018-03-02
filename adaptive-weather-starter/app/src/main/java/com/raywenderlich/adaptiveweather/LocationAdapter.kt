/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.adaptiveweather

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LocationAdapter(private val context: Context, private val dataSet: List<Location>,
                      private val listener: OnItemClickListener)
  : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

  private var selectedLocation: Location? = null

  var selectedLocationIndex: Int
    get() = dataSet.indexOf(selectedLocation)
    set(index) {
      this.selectedLocation = dataSet[index]
    }

  interface OnItemClickListener {
    fun onItemClick(location: Location)
  }

  class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var title: TextView = v.findViewById<View>(android.R.id.text1) as TextView
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(android.R.layout.simple_selectable_list_item, parent, false)

    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.title.text = dataSet[position].name
    holder.title.textSize = 22f
    holder.itemView.setOnClickListener {
      listener.onItemClick(dataSet[position])
      selectedLocation = dataSet[position]
      notifyDataSetChanged()
    }
    if (dataSet[position] == selectedLocation) {
      val backgroundColor = ContextCompat.getColor(context, R.color.color_primary_dark)
      holder.itemView.setBackgroundColor(backgroundColor)
      holder.title.setTextColor(Color.WHITE)
    } else {
      holder.itemView.setBackgroundColor(Color.WHITE)
      holder.title.setTextColor(Color.BLACK)
    }
  }

  override fun getItemCount() = dataSet.size
}
