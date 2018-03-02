package com.raywenderlich.android.alltherecipes.views

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.android.alltherecipes.R
import com.raywenderlich.android.alltherecipes.models.Recipe
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val title = intent.extras.getString(EXTRA_TITLE)
        val url = intent.extras.getString(EXTRA_URL)

        setTitle(title)

        detail_web_view.loadUrl(url)
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"

        fun newInstance(context: Context, recipe: Recipe): Intent {
            val intent = Intent(context, RecipeDetailActivity::class.java)

            intent.putExtra(EXTRA_TITLE, recipe.title)
            intent.putExtra(EXTRA_URL, recipe.instructionUrl)

            return intent
        }
    }
}
