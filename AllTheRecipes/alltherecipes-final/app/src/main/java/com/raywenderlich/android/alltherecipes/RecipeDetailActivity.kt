package com.raywenderlich.android.alltherecipes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView


class RecipeDetailActivity : AppCompatActivity() {

  private lateinit var webView: WebView

  companion object {
    const val EXTRA_TITLE = "title"
    const val EXTRA_URL = "url"

    fun newIntent(context: Context, recipe: Recipe): Intent {
      val detailIntent = Intent(context, RecipeDetailActivity::class.java)

      detailIntent.putExtra(EXTRA_TITLE, recipe.title)
      detailIntent.putExtra(EXTRA_URL, recipe.instructionUrl)

      return detailIntent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_detail)

    val title = intent.extras.getString(EXTRA_TITLE)
    val url = intent.extras.getString(EXTRA_URL)

    setTitle(title)

    webView = findViewById(R.id.detail_web_view)

    webView.loadUrl(url)
  }
}
