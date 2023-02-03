package com.mgpersia.androidbox.data.model.information

import android.os.Parcelable
import com.mgpersia.androidbox.data.model.FilmWithGeneral
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetActorDetailInformation(
    val bdate: String?,
    val bio: String?,
    val bioـen: String?,
    val country: String?,
    val films: List<FilmWithGeneral?>?,
    val id: Int?,
    val image_path: String?,
    val imdb: String?,
    var is_followed_user: Boolean?,
    val name: String?,
    val name_en: String?,
    val occupation: String?,
    val rottentomatoes: String?
): Parcelable