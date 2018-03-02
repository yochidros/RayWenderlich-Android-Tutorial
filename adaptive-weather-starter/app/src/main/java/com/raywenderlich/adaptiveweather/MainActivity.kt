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

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.android.flexbox.FlexboxLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class MainActivity : AppCompatActivity() {

  private val locations = ArrayList<Location>()
  private lateinit var locationAdapter: LocationAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupRecyclerView()

    if (savedInstanceState != null) {
      val index = savedInstanceState.getInt(SELECTED_LOCATION_INDEX)
      if ( index >= 0 && index < locations.size) {
        locationAdapter.selectedLocationIndex = index
        loadForecast(locations[index].forecast)
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(SELECTED_LOCATION_INDEX, locationAdapter.selectedLocationIndex)
  }

  private fun setupRecyclerView() {
    val recyclerView = findViewById<View>(R.id.list) as RecyclerView
    recyclerView.setHasFixedSize(true)

    val layoutManager = LinearLayoutManager(this)
    val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
    recyclerView.addItemDecoration(dividerItemDecoration)
    recyclerView.layoutManager = layoutManager

    loadData()

    locationAdapter = LocationAdapter(this, locations, object : LocationAdapter.OnItemClickListener {
      override fun onItemClick(location: Location) {
          loadForecast(location.forecast)
      }
    })
    recyclerView.adapter = locationAdapter
  }

  private fun loadData() {
    val json: String? = loadJsonString()
    val array: JSONArray? = loadJsonArray(json)
    loadLocations(array)
  }

  private fun loadJsonString(): String? {
    var json: String? = null
    try {
      val inputStream = assets.open("data.json")
      val size = inputStream.available()
      val buffer = ByteArray(size)
      inputStream.read(buffer)
      inputStream.close()
      json = String(buffer, Charset.forName("UTF-8"))
    } catch (e: IOException) {
      Log.e("MainActivity", e.toString())
    }
    return json
  }

  private fun loadJsonArray(json: String?): JSONArray? {
    var array: JSONArray? = null
    try {
      array = JSONArray(json)
    } catch (e: JSONException) {
      Log.e("MainActivity", e.toString())
    }
    return array
  }

  private fun loadLocations(array: JSONArray?) {
    if (array != null) {
      for (i in 0 until array.length()) {
        try {
          val jsonObject = array[i] as JSONObject
          val stringArray = jsonObject["forecast"] as JSONArray
          val forecast = (0 until stringArray.length()).mapTo(ArrayList<String>()) { stringArray.getString(it) }
          val location = Location(jsonObject["name"] as String, forecast)
          locations.add(location)
        } catch (e: JSONException) {
          e.printStackTrace()
        }
      }
    }
  }

  private fun loadForecast(forecast: List<String>) {
    val forecastView = findViewById<View>(R.id.forecast) as FlexboxLayout
    for ( i in 0 until forecastView.childCount) {
      val dayView = forecastView.getChildAt(i) as AppCompatImageView
      dayView.setImageDrawable(mapWeatherToDrawable(forecast[i]))
    }
  }

  private fun mapWeatherToDrawable(forecast: String): Drawable? {
    var drawableId = 0
    when(forecast) {
      "sun" -> drawableId = R.drawable.ic_sun
      "rain" -> drawableId = R.drawable.ic_rain
      "fog" -> drawableId = R.drawable.ic_fog
      "thunder" -> drawableId = R.drawable.ic_thunder
      "cloud" -> drawableId = R.drawable.ic_cloud
      "snow" -> drawableId = R.drawable.ic_snow
    }
    return ContextCompat.getDrawable(this, drawableId)
  }

  companion object {
      private const val SELECTED_LOCATION_INDEX = "selctedLocationIndex"
  }
}
