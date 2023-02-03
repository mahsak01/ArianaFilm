package com.mgpersia.androidbox.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.data.source.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalLocalDataSource: LocalDataSource {

    @Insert
    override fun addFilm(localFilmDownload: LocalFilmDownload): Completable


    @Query("SELECT * FROM LocalFilmDownload WHERE username= :username")
    override fun getAllFilm(username: String): Single<List<LocalFilmDownload>>
}