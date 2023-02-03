package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val cover: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val name_en: String?,
    val is_dubbed:Boolean?=null,
    val has_subtitle:Boolean?=null,
    val imdb_id:String?=null
):Parcelable