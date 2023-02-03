package com.mgpersia.androidbox.data.repository

import com.mgpersia.androidbox.data.model.LocalFilmDownload
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRepository {

    fun addFilm(localFilmDownload: LocalFilmDownload): Completable

    fun getAllFilm(username: String): Single<List<LocalFilmDownload>>

}