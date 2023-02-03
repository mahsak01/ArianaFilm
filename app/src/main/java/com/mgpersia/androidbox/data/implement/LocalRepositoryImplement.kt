package com.mgpersia.androidbox.data.implement

import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.data.repository.LocalRepository
import com.mgpersia.androidbox.data.source.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalRepositoryImplement(private val localDataSource: LocalDataSource) :
    LocalRepository {

    override fun addFilm(localFilmDownload: LocalFilmDownload): Completable =
        localDataSource.addFilm(localFilmDownload)

    override fun getAllFilm(username: String): Single<List<LocalFilmDownload>> =
        localDataSource.getAllFilm(username)
}