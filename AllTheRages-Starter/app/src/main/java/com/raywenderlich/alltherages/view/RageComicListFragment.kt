/**
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

package com.raywenderlich.alltherages.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.alltherages.R

import com.raywenderlich.alltherages.databinding.RecyclerItemRageComicBinding
import com.raywenderlich.alltherages.model.Comic


class RageComicListFragment : Fragment() {

  interface OnRageComicSelected {
    fun onRageComicSelected(comic: Comic)
  }

  private lateinit var listener: OnRageComicSelected

  private lateinit var imageResIds: IntArray
  private lateinit var names: Array<String>
  private lateinit var descriptions: Array<String>
  private lateinit var urls: Array<String>

  override fun onAttach(context: Context?) {
    super.onAttach(context)

    val resouces = context!!.resources
    names = resources.getStringArray(R.array.names)
    descriptions = resouces.getStringArray(R.array.descriptions)
    urls = resources.getStringArray(R.array.urls)

    val typedArray = resources.obtainTypedArray(R.array.images)
    val imageCount = names.size
    imageResIds = IntArray(imageCount)

    for ( i in 0..imageCount-1) {
      imageResIds[i] = typedArray.getResourceId(i, 0)
    }

    typedArray.recycle()

    if (context is OnRageComicSelected) {
      listener = context
    } else {
      throw ClassCastException(context.toString() + " must implement OnRageComicSelected")
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view: View = inflater!!.inflate(R.layout.fragment_rage_comic_list, container, false)
    val activity = activity
    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView
    recyclerView.layoutManager = GridLayoutManager(activity, 2)
    recyclerView.adapter = RageComicAdapter(activity)
    return view
  }


  internal inner class RageComicAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val layoutInflater: LayoutInflater

    init {
      layoutInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
      val recyclerItemRageComicBinding = RecyclerItemRageComicBinding.inflate(layoutInflater,
          viewGroup, false)
      return ViewHolder(recyclerItemRageComicBinding.root, recyclerItemRageComicBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      val comic = Comic(imageResIds[position], names[position],
              descriptions[position], urls[position])
      viewHolder.setData(comic)
        viewHolder.itemView.setOnClickListener { listener.onRageComicSelected(comic) }
    }

    override fun getItemCount(): Int {
      return names.size
    }
  }

  internal inner class ViewHolder constructor(itemView: View,
                                              val recyclerItemRageComicBinding:
                                              RecyclerItemRageComicBinding) :
      RecyclerView.ViewHolder(itemView) {

    fun setData(comic: Comic) {
      recyclerItemRageComicBinding.comic = comic
    }
  }

  companion object {

    fun newInstance(): RageComicListFragment {
      return RageComicListFragment()
    }
  }
}
