package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class General(
    val cover: String?,
    val first_color: String?,
    val id: Int?,
    val is_tag: Boolean?,
    val name: String?,
    val name_en: String?,
    val second_color: String?
):Parcelable