package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val date: String?,
    val duration: Int?,
    val episode_Files: List<EpisodeFile?>?,
    val id: Int?,
    val name: String?,
    val name_en: String?,
    val name_s: String?,
    val season: Int?
):Parcelable