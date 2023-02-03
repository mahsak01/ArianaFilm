package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Live(
    val baner: String?,
    val created_at: String?,
    val description: String?,
    val en_name: String?,
    val fa_name: String?,
    val icon: String?,
    val id: Int?,
    val logo: String?,
    val modified_at: String?,
    val url: String?
): Parcelable