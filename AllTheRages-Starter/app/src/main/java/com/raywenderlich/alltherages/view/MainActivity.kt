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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.raywenderlich.alltherages.R
import com.raywenderlich.alltherages.model.Comic

class MainActivity : AppCompatActivity(), RageComicListFragment.OnRageComicSelected {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager
              .beginTransaction()
              .add(R.id.root_layout, RageComicListFragment.newInstance(), "rageComicList")
              .commit()
    }
  }

  override fun onRageComicSelected(comic: Comic) {
    val detailFragment = RageComicDetailsFragment.newInstance(comic)
    supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, detailFragment, "rageComicDetail")
            .addToBackStack(null)
            .commit()
  }
}

