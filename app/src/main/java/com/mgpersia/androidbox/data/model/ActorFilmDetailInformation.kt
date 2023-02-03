package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActorFilmDetailInformation(
    val id: Int?,
    val image_path: String?,
    val name: String?,
    val name_en: String?
): Parcelable