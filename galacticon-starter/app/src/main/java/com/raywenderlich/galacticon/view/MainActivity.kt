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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.galacticon.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.raywenderlich.galacticon.api.ImageRequester
import com.raywenderlich.galacticon.model.Photo
import com.raywenderlich.galacticon.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), ImageRequester.ImageRequesterResponse {
  private lateinit var linearLayoutManager: LinearLayoutManager
  private lateinit var adapter: RecyclerAdapter
  private lateinit var gridLayoutManager: GridLayoutManager

  private var photosList: ArrayList<Photo> = ArrayList()
  private lateinit var imageRequester: ImageRequester
  private val lastVisibleItemPosition: Int
    get() = if (recyclerView.layoutManager == linearLayoutManager) {
      linearLayoutManager.findLastVisibleItemPosition()
    } else {
      gridLayoutManager.findLastVisibleItemPosition()
    }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item!!.itemId == R.id.action_change_recycler_manager) {
      changeLayoutManager()
      return true
    }
    return super.onOptionsItemSelected(item)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    imageRequester = ImageRequester(this)

    linearLayoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = linearLayoutManager
    setRecyclerViewScrollListener()

    adapter = RecyclerAdapter(photosList)
    recyclerView.adapter = adapter

    gridLayoutManager = GridLayoutManager(this, 2)

    setRecyclerViewItemTouchListener()
  }

  override fun onStart() {
    super.onStart()
    if (photosList.size == 0) {
      requestPhoto()
    }
  }

  private fun requestPhoto() {
    try {
      imageRequester.getPhoto()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  private fun setRecyclerViewScrollListener() {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val totalItemCount = recyclerView!!.layoutManager.itemCount
        if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
          requestPhoto()
        }
      }
    })
  }

  private fun setRecyclerViewItemTouchListener() {
    val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
      override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        val position = viewHolder?.adapterPosition
        if (position != null) {
          photosList.removeAt(position)
          recyclerView.adapter.notifyItemRemoved(position)
        }
      }
    }
    val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
    itemTouchHelper.attachToRecyclerView(recyclerView)
  }

  private fun changeLayoutManager() {
    if (recyclerView.layoutManager == linearLayoutManager) {
      recyclerView.layoutManager = gridLayoutManager

      if (photosList.size == 1) {
        requestPhoto()
      }
    } else {
      recyclerView.layoutManager = linearLayoutManager
    }
  }

  override fun receivedNewPhoto(newPhoto: Photo) {
    runOnUiThread {
      photosList.add(newPhoto)
      adapter.notifyItemInserted(photosList.size)
    }
  }
}
