package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Image

data class GetAllImageFilmHomeInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Image?>?
)