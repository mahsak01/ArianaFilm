package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Actor

data class GetAllActorInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Actor?>?
)