package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Following

data class GetAllFollowingInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Following?>?
)