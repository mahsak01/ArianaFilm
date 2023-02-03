package com.mgpersia.androidbox.data.implement

import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.Country
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.repository.MainRepository
import com.mgpersia.androidbox.data.source.MainDataSource
import io.reactivex.Single

class MainRepositoryImplement(private val mainRemoteDataSource: MainDataSource) :
    MainRepository {

    override fun getOpt(phone: String, country: String): Single<GetOptInformation> =
        mainRemoteDataSource.getOpt(phone, country)

    override fun checkOpt(information: CheckOptInformation): Single<GetCheckOptInformation> =
        mainRemoteDataSource.checkOpt(information)

    override fun getLocation(): Single<CheckLocationInformation> =
        mainRemoteDataSource.getLocation()

    override fun registerUser(
        registerUserInformation: RegisterUserInformation
    ): Single<GetRegisterUserResultInformation> =
        mainRemoteDataSource.registerUser(
            registerUserInformation
        )

    override fun getAllGenre(): Single<GetAllGenreInformation> = mainRemoteDataSource.getAllGenre()

    override fun getAllTag(): Single<GetAllGenreInformation> = mainRemoteDataSource.getAllTag()

    override fun getAllCountries(): Single<List<Country>> = mainRemoteDataSource.getAllCountries()

    override fun getAllCountriesFilms(): Single<List<Country>> =
        mainRemoteDataSource.getAllCountriesFilms()

    override fun getLives(): Single<GetLiveInformation> = mainRemoteDataSource.getLives()

    override fun getFilmDetail(id: String): Single<GetFilmDetailInformation> =
        mainRemoteDataSource.getFilmDetail(id)

    override fun getBannerHome(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getBannerHome()

    override fun getNewFilm(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getNewFilm()

    override fun getNewSeries(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getNewSeries()

    override fun getNewlySeries(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getNewlySeries()

    override fun getCommingSoon(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getCommingSoon()

    override fun getPopularActor(): Single<List<ActorFilmDetailInformation>> =
        mainRemoteDataSource.getPopularActor()

    override fun getPopularDirector(): Single<List<ActorFilmDetailInformation>> =
        mainRemoteDataSource.getPopularDirector()

    override fun getAllActor(): Single<GetAllActorInformation> =
        mainRemoteDataSource.getAllActor()

    override fun getAllDirector(): Single<GetAllActorInformation> =
        mainRemoteDataSource.getAllDirector()

    override fun getPopularFilm(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getPopularFilm()

    override fun getPopularMovie(): Single<GetAllImageFilmHomeInformation> =
        mainRemoteDataSource.getPopularMovie()

    override fun getActorDetail(id: String): Single<GetActorDetailInformation> =
        mainRemoteDataSource.getActorDetail(id)

    override fun getAllComment(id: String): Single<GetAllCommentOfFilmInformation> =
        mainRemoteDataSource.getAllComment(id)

    override fun getAge(): Single<GetAgeLimitInformation> = mainRemoteDataSource.getAge()

    override fun getSearch(
        dubbed: String,
        type: String,
        subtitle: String,
        age: String,
        toyear: String,
        fromyear: String,
        tag: String,
        genre: String,
        country: String,
        page: String,
        page_size: String,
        text: String
    ): Single<GetFilterInformation> = mainRemoteDataSource.getSearch(
        dubbed,
        type,
        subtitle,
        age,
        toyear,
        fromyear,
        tag,
        genre,
        country,
        page,
        page_size,
        text
    )
}