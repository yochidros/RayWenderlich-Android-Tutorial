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
import com.raywenderlich.galacticon.model.Photo
import com.raywenderlich.galacticon.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

  private var selectedPhoto: Photo? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_photo)

    selectedPhoto = intent.getSerializableExtra(PHOTO_KEY) as Photo
    Picasso.with(this).load(selectedPhoto?.url).into(photoImageView)

    photoDescription?.text = selectedPhoto?.explanation
  }

  companion object {
    private val PHOTO_KEY = "PHOTO"
  }
}
