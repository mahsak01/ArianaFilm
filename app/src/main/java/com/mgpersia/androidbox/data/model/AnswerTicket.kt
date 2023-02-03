package com.mgpersia.androidbox.data.model

data class AnswerTicket(
    val answer_status: String?,
    val attachment: Any?,
    val created_at: String?,
    val id: Int?,
    val message: String?,
    val modified_at: String?,
    val staff: String?,
    val ticket: Ticket?
)