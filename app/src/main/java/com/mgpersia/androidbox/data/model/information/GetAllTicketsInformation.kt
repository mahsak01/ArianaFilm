package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Ticket

data class GetAllTicketsInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Ticket?>?
)