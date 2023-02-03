package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import com.mgpersia.androidbox.data.FilmId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val image: String?,
    val cover: String?,
    val name_en: String?,
    val film: FilmId?=null,
    val is_dubbed:Boolean?=null,
    val has_subtitle:Boolean?=null,
    val imdb_id:String?=null
) : Parcelable