package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "LocalFilmDownload")
@Parcelize
class LocalFilmDownload(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "id_film")
    val idFilm: Int?,
    val name: String?,
    val name_en: String?,
    val cover: String?,
    val username: String?,
    @ColumnInfo(name = "film_address")
    val filmAddress: String?,
    @ColumnInfo(name = "is_movie")
    val isMovie: Boolean,
    @ColumnInfo(name = "is_download")
    val isDownload: Boolean = false,
) : Parcelable {
}