/*
 * Copyright (c) 2017 Razeware LLC
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

package com.raywenderlich.marsrovers.recyclerview

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val viewMap: MutableMap<Int, View> = HashMap()

  init {
    findViewItems(itemView)
  }

  fun setText(@IdRes id: Int, text: String) {
    val view = (viewMap[id] ?: throw IllegalArgumentException("View for $id not found")) as? TextView ?: throw IllegalArgumentException("View for $id is not a TextView")
    view.text = text
  }

  fun getImage(@IdRes id: Int): ImageView {
    return (viewMap[id] ?: throw IllegalArgumentException("View for $id not found")) as? ImageView ?: throw IllegalArgumentException("View for $id is not a ImageView")
  }

  private fun findViewItems(itemView: View) {
    addToMap(itemView)
    if (itemView is ViewGroup) {
      val childCount = itemView.childCount
      (0 until childCount)
          .map { itemView.getChildAt(it) }
          .forEach { findViewItems(it) }
    }
  }

  private fun addToMap(itemView: View) {
    if (itemView.id == View.NO_ID) {
      itemView.id = View.generateViewId()
    }
    viewMap.put(itemView.id, itemView)
  }

}
