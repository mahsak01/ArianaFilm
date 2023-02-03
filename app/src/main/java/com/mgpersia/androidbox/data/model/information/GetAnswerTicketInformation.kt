package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.AnswerTicket

data class GetAnswerTicketInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<AnswerTicket?>?
)