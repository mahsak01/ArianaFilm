package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.AgeLimit

data class GetAgeLimitInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<AgeLimit?>?
)