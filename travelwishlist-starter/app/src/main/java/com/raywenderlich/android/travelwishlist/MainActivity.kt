package com.raywenderlich.android.travelwishlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.support.v4.util.Pair
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {
  lateinit private var adapter: TravelListAdapter
  lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
  lateinit private var menu: Menu
  private var isListView: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setUpActionBar()
    isListView = true

    staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    list.layoutManager = staggeredLayoutManager
    adapter = TravelListAdapter(this)
    list.adapter = adapter
    adapter.setitemClickListener(this.onItemClickListener)
  }

  private fun setUpActionBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    supportActionBar?.setDisplayShowTitleEnabled(true)
    supportActionBar?.elevation = 7.0f
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    this.menu = menu
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if (id == R.id.action_toggle) {
      toggle()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun toggle() {
    if (isListView) {
      staggeredLayoutManager.spanCount = 2
      showGridView()
    } else {
      staggeredLayoutManager.spanCount = 1
      showListView()
    }
  }

  private fun showListView() {
    val item = menu.findItem(R.id.action_toggle)
    item.setIcon(R.drawable.ic_action_grid)
    item.title = getString(R.string.show_as_grid)
    isListView = true
  }

  private fun showGridView() {
    val item = menu.findItem(R.id.action_toggle)
    item.setIcon(R.drawable.ic_action_list)
    item.title = getString(R.string.show_as_list)
    isListView = false
  }

  private val onItemClickListener = object : TravelListAdapter.OnItemClickListener {
    override fun onItemClick(view: View, position: Int) {
      val intent = DetailActivity.newIntent(this@MainActivity, position)

      val placeImage = view.findViewById<ImageView>(R.id.placeImage)
      val placeNameHolder = view.findViewById<LinearLayout>(R.id.placeNameHolder)

      val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
      val statusBar = findViewById<View>(android.R.id.statusBarBackground)

      val naviPair = Pair(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)
      val statusPair = Pair(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
      val toolPair = Pair(toolbar as View, "tActionBar")
      val imagePair = Pair(placeImage as View, "tImage")
      val placeNameHolderPair = Pair(placeNameHolder as View, "tNameHolder")

      val pairs = mutableListOf(imagePair, placeNameHolderPair, statusPair, toolPair)
      if (navigationBar != null && naviPair != null) {
        pairs.add(naviPair)
      }

      val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, *pairs.toTypedArray())
      ActivityCompat.startActivity(this@MainActivity, intent, options.toBundle())
    }
  }
}
