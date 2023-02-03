package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeFile(
    val name: String?,
    val name_en: String?,
    val quality_id: String?,
    val subtitle_id: Int?
):Parcelable