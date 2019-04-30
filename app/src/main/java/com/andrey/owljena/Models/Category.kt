package com.andrey.owljena.Models

class Category(val categoryName: String) {
    var subcategoryArray = ArrayList<SubCategory>()
}

class SubCategory(var subcategoryName: String) {
    var selected = false
}