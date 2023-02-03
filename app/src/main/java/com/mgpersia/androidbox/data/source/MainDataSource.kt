package com.mgpersia.androidbox.data.source

import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.Country
import com.mgpersia.androidbox.data.model.information.*
import io.reactivex.Single

interface MainDataSource {
    fun getOpt(phone: String, country: String): Single<GetOptInformation>

    fun checkOpt(information: CheckOptInformation): Single<GetCheckOptInformation>

    fun getLocation(): Single<CheckLocationInformation>

    fun registerUser(
        registerUserInformation: RegisterUserInformation
    ): Single<GetRegisterUserResultInformation>

    fun getAllGenre(): Single<GetAllGenreInformation>

    fun getAllTag(): Single<GetAllGenreInformation>

    fun getAllCountries(): Single<List<Country>>

    fun getAllCountriesFilms(): Single<List<Country>>

    fun getLives(): Single<GetLiveInformation>

    fun getFilmDetail(id: String): Single<GetFilmDetailInformation>

    fun getBannerHome(): Single<GetAllImageFilmHomeInformation>

    fun getNewFilm(): Single<GetAllImageFilmHomeInformation>

    fun getNewSeries(): Single<GetAllImageFilmHomeInformation>

    fun getNewlySeries(): Single<GetAllImageFilmHomeInformation>

    fun getCommingSoon(): Single<GetAllImageFilmHomeInformation>

    fun getPopularActor(): Single<List<ActorFilmDetailInformation>>

    fun getPopularDirector(): Single<List<ActorFilmDetailInformation>>

    fun getAllActor(): Single<GetAllActorInformation>

    fun getAllDirector(): Single<GetAllActorInformation>

    fun getPopularFilm(): Single<GetAllImageFilmHomeInformation>

    fun getPopularMovie(): Single<GetAllImageFilmHomeInformation>

    fun getActorDetail(id: String): Single<GetActorDetailInformation>

    fun getAllComment(id: String): Single<GetAllCommentOfFilmInformation>

    fun getAge(): Single<GetAgeLimitInformation>

    fun getSearch(
        dubbed: String,
        type: String,
        subtitle: String,
        age: String,
        toyear: String,
        fromyear: String,
        tag:String,
        genre:String,
        country: String,
        page: String,
        page_size: String,
        text: String,
    ): Single<GetFilterInformation>

}