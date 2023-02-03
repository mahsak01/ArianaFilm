package com.mgpersia.androidbox.data.source.remote

import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.Country
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.source.MainDataSource
import com.mgpersia.androidbox.service.http.ApiService
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class MainRemoteDataSource(private var apiService: ApiService) : MainDataSource {

    override fun getOpt(phone: String, country: String): Single<GetOptInformation> =
        apiService.getOpt(phone, country)

    override fun checkOpt(information: CheckOptInformation): Single<GetCheckOptInformation> =
        apiService.checkOpt(information)

    override fun getLocation(): Single<CheckLocationInformation> = apiService.getLocation()

    override fun registerUser(
        registerUserInformation: RegisterUserInformation
    ): Single<GetRegisterUserResultInformation> =
        apiService.registerUser(registerUserInformation)


    override fun getAllGenre(): Single<GetAllGenreInformation> = apiService.getAllGenre()

    override fun getAllTag(): Single<GetAllGenreInformation> = apiService.getAllTag()


    override fun getAllCountries(): Single<List<Country>> = apiService.getAllCountries()

    override fun getAllCountriesFilms(): Single<List<Country>> = apiService.getAllCountriesFilms()

    override fun getLives(): Single<GetLiveInformation> = apiService.getLives()

    override fun getFilmDetail(id: String): Single<GetFilmDetailInformation> =
        apiService.getFilmDetail(id)

    override fun getBannerHome(): Single<GetAllImageFilmHomeInformation> =
        apiService.getBannerHome()

    override fun getNewFilm(): Single<GetAllImageFilmHomeInformation> = apiService.getNewFilm()

    override fun getNewSeries(): Single<GetAllImageFilmHomeInformation> = apiService.getNewSeries()

    override fun getNewlySeries(): Single<GetAllImageFilmHomeInformation> =
        apiService.getNewlySeries()

    override fun getCommingSoon(): Single<GetAllImageFilmHomeInformation> =
        apiService.getCommingSoon()

    override fun getPopularActor(): Single<List<ActorFilmDetailInformation>> =
        apiService.getPopularActor()

    override fun getPopularDirector(): Single<List<ActorFilmDetailInformation>> =
        apiService.getPopularDirector()

    override fun getAllActor(): Single<GetAllActorInformation> = apiService.getAllActor()

    override fun getAllDirector(): Single<GetAllActorInformation> =
        apiService.getAllDirector()

    override fun getPopularFilm(): Single<GetAllImageFilmHomeInformation> =
        apiService.getPopularFilm()

    override fun getPopularMovie(): Single<GetAllImageFilmHomeInformation> =
        apiService.getPopularMovie()

    override fun getActorDetail(id: String): Single<GetActorDetailInformation> =
        apiService.getActorDetail(id)

    override fun getAllComment(id: String): Single<GetAllCommentOfFilmInformation> =
        apiService.getAllComment(id)

    override fun getAge(): Single<GetAgeLimitInformation> = apiService.getAge()

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
        text: String,
    ): Single<GetFilterInformation> = apiService.getSearch(
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