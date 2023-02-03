package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season(
    val id: Int?,
    val date: String?,
    val duration: Int?,
    val episodes: List<Episode>?,
    val film: Int?,
    val name: String?,
    val name_en: String?,
    val name_s: String?,
    val number_of_episodes: Int?
):Parcelable