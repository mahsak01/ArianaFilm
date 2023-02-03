package com.mgpersia.androidbox.data.source

import com.mgpersia.androidbox.data.model.LocalFilmDownload
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {

    fun addFilm(localFilmDownload: LocalFilmDownload): Completable

    fun getAllFilm(username: String): Single<List<LocalFilmDownload>>

}