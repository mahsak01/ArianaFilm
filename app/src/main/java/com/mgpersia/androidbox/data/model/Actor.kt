package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    val id: Int?,
    val bdate: String?,
    val bio: String?,
    val bioـen: String?,
    val country: String?,
    val films: List<Film?>?,
    val image_path: String?,
    val imdb: String?,
    val is_followed_user: Boolean?,
    val name: String?,
    val name_en: String?,
    val occupation: String?,
    val rottentomatoes: String?
):Parcelable