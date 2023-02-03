package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
    val attachment: String?,
    val created_at: String?,
    val department: Department?,
    val id: Int?,
    val message: String?,
    val modified_at: String?,
    val priority: String?,
    val raised_by: String?,
    val status: String?,
    val subject: String?
): Parcelable