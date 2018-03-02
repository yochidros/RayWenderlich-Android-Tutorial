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

package com.raywenderlich.galacticon.api

import android.app.Activity
import android.content.Context
import android.net.Uri.Builder
import com.raywenderlich.galacticon.R
import com.raywenderlich.galacticon.model.Photo
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageRequester(listeningActivity: Activity) {

  interface ImageRequesterResponse {
    fun receivedNewPhoto(newPhoto: Photo)
  }

  private val calendar: Calendar = Calendar.getInstance()
  private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
  private val responseListener: ImageRequesterResponse
  private val context: Context
  private val client: OkHttpClient
  var isLoadingData: Boolean = false
    private set

  init {
    responseListener = listeningActivity as ImageRequesterResponse
    context = listeningActivity.applicationContext
    client = OkHttpClient()
    calendar.set(2016,5,12)
  }

  fun getPhoto() {

    val date = dateFormat.format(calendar.time)

    val urlRequest = Builder().scheme(URL_SCHEME)
        .authority(URL_AUTHORITY)
        .appendPath(URL_PATH_1)
        .appendPath(URL_PATH_2)
        .appendQueryParameter(URL_QUERY_PARAM_DATE_KEY, date)
        .appendQueryParameter(URL_QUERY_PARAM_API_KEY, context.getString(R.string.api_key))
        .build().toString()

    val request = Request.Builder().url(urlRequest).build()
    isLoadingData = true

    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        isLoadingData = false
        e.printStackTrace()
      }

      override fun onResponse(call: Call, response: Response) {

        try {
          println(response)
          val photoJSON = JSONObject(response.body()!!.string())

          calendar.add(Calendar.DAY_OF_YEAR, -1)

          if (photoJSON.getString(MEDIA_TYPE_KEY) != MEDIA_TYPE_VIDEO_VALUE) {
            val receivedPhoto = Photo(photoJSON)
            responseListener.receivedNewPhoto(receivedPhoto)
            isLoadingData = false
          } else {
            getPhoto()
          }
        } catch (e: JSONException) {
          isLoadingData = false
          e.printStackTrace()
        }

      }
    })
  }

  companion object {
    private val MEDIA_TYPE_KEY = "media_type"
    private val MEDIA_TYPE_VIDEO_VALUE = "video"
    private val URL_SCHEME = "https"
    private val URL_AUTHORITY = "api.nasa.gov"
    private val URL_PATH_1 = "planetary"
    private val URL_PATH_2 = "apod"
    private val URL_QUERY_PARAM_DATE_KEY = "date"
    private val URL_QUERY_PARAM_API_KEY = "api_key"
  }
}
