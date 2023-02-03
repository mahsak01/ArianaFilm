package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Notification

data class GetAllNotificationInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Notification?>?
)