package com.andrey.owljena.Models

import android.content.Context
import com.andrey.owljena.R

class RoadData(val context: Context) {

    val CAR = 0
    val CAR_GADGETS = 1
    val SIGN = 2
    val TRAFFIC_LIGHT = 3

    val roadBuildArray: ArrayList<Category>

    init {
        roadBuildArray = setList()
    }

    private fun setList(): ArrayList<Category> {
        val data = ArrayList<Category>()
        val categories = context.resources.getStringArray(R.array.category)

        for (i in categories.indices) {
            val categoryArray = getCategory(i)
            val category = Category(categories.get(i))
            for (j in categoryArray.indices) {

                val subcategory = SubCategory(categoryArray.get(j))
                subcategory.selected = false
                category.subcategoryArray.add(subcategory)
            }
            data.add(category)
        }
        return data
    }

    private fun getCategory(category: Int) =
        when (category) {
            CAR -> context.resources.getStringArray(R.array.car).toCollection(ArrayList())
            CAR_GADGETS -> context.resources.getStringArray(R.array.carGadget).toCollection(ArrayList())
            SIGN -> context.resources.getStringArray(R.array.sign).toCollection(ArrayList())
            TRAFFIC_LIGHT -> context.resources.getStringArray(R.array.trafficLight).toCollection(ArrayList())
            else -> ArrayList<String>()
        }
}