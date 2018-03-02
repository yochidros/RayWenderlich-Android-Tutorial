package com.raywenderlich.android.alltherecipes.views.RecipeList

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.raywenderlich.android.alltherecipes.R
import com.raywenderlich.android.alltherecipes.models.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_recipe.view.*

/**
 * Created by yochio on 2018/03/01.
 */

class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<Recipe>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)

        val recipe = getItem(position) as Recipe

        rowView.recipe_list_title.text = recipe.title
        rowView.recipe_list_subtitle.text = recipe.description
        rowView.recipe_list_detail.text = recipe.label

        rowView.recipe_list_title.typeface = ResourcesCompat.getFont(context, R.font.josefinsans_bold)
        rowView.recipe_list_subtitle.typeface = ResourcesCompat.getFont(context, R.font.josefinsans_semibolditalic)
        rowView.recipe_list_detail.typeface = ResourcesCompat.getFont(context, R.font.quicksand_bold)

        rowView.recipe_list_detail.setTextColor(ContextCompat.getColor(context, LABEL_COLORS[recipe.label] ?: R.color.colorPrimary))

        Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(rowView.recipe_list_thumbnail)
        return rowView
    }



    companion object {
        private val LABEL_COLORS = hashMapOf(
                "Low-Carb" to R.color.colorLowCarb,
                "Low-Fat" to R.color.colorLowFat,
                "Low-Sodium" to R.color.colorLowSodium,
                "Medium-Carb" to R.color.colorMediumCarb,
                "Vegetarian" to R.color.colorVegetarian,
                "Balanced" to R.color.colorBalanced
        )
    }
}