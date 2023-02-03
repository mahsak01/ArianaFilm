package com.mgpersia.androidbox.common

import com.mgpersia.androidbox.data.model.FilterBottomSheet
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.Resolution
import com.mgpersia.androidbox.data.model.information.GetFilterInformation

object MainContainer {

    var filterItems = ArrayList<FilterBottomSheet>()

    var setFilter = false

    private var sortItems: FilterBottomSheet? = null

    var startYear: String? = null

    var endYear: String? = null

    var filmSearchList = ArrayList<Image>()

    var serialSearchList = ArrayList<Image>()

    var resolution = null

    var seen = false

    fun updateYear(startYear: String, endYear: String) {
        this.startYear = startYear
        this.endYear = endYear
    }


    fun updateFilterItems(filterItem: FilterBottomSheet) {
        for (item in filterItems) {
            if (item.title == filterItem.title) {
                filterItem.itemSelect = filterItem.itemSelect
                return
            }
        }
    }


    fun setSortItems(sortItem: FilterBottomSheet) {
        this.sortItems = sortItems
    }

}