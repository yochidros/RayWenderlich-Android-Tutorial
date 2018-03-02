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

package com.raywenderlich.galacticon.model

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Photo(photoJSON: JSONObject) : Serializable {

  private lateinit var photoDate: String
  lateinit var humanDate: String
    private set
  lateinit var explanation: String
    private set
  lateinit var url: String
    private set

  init {
    try {
      photoDate = photoJSON.getString(PHOTO_DATE)
      humanDate = convertDateToHumanDate()
      explanation = photoJSON.getString(PHOTO_EXPLANATION)
      url = photoJSON.getString(PHOTO_URL)
    } catch (e: JSONException) {
      e.printStackTrace()
    }

  }

  private fun convertDateToHumanDate(): String {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val humanDateFormat = SimpleDateFormat("dd MMMM yyyy")
    try {
      val parsedDateFormat = dateFormat.parse(photoDate)
      val cal = Calendar.getInstance()
      cal.time = parsedDateFormat
      return humanDateFormat.format(cal.time)
    } catch (e: ParseException) {
      e.printStackTrace()
      return ""
    }

  }

  companion object {
    private val PHOTO_DATE = "date"
    private val PHOTO_EXPLANATION = "explanation"
    private val PHOTO_URL = "url"
  }
}
