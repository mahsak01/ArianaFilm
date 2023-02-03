package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Film

data class GetFilterInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    var results: ArrayList<Film>?
)