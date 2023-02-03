package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.General

data class GetAllGenreInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<General?>?
)