package com.mgpersia.androidbox.data.model

data class FilterBottomSheet(
    val title: String,
    val items: ArrayList<FilterOptionBottomSheet>,
    var itemSelect: ArrayList<Int>
)